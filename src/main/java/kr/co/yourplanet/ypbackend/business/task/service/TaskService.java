package kr.co.yourplanet.ypbackend.business.task.service;

import kr.co.yourplanet.ypbackend.business.task.domain.Task;
import kr.co.yourplanet.ypbackend.business.task.domain.TaskHistory;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskAcceptForm;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskNegotiateForm;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskRejectForm;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskRequestForm;
import kr.co.yourplanet.ypbackend.business.task.repository.TaskRepository;
import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void requestTask(TaskRequestForm taskRequestForm, Long advertiserId) {
        Member advertiser = memberRepository.findMemberById(advertiserId);
        Member author = memberRepository.findMemberById(taskRequestForm.getAuthorId());

        if (advertiser == null || !MemberType.ADVERTISER.equals(advertiser.getMemberType())) {
            throw new IllegalArgumentException("유효하지 않은 광고주 정보");
        }

        if (author == null || !MemberType.AUTHOR.equals(author.getMemberType())) {
            throw new IllegalArgumentException("유효하지 않은 작가 정보");
        }

        Task task = Task.builder()
                .author(author)
                .advertiser(advertiser)
                .taskStatus(TaskStatus.REQUEST)
                .build();

        taskRepository.saveTask(task);

        TaskHistory taskHistory = TaskHistory.builder()
                .task(task)
                .seq(1)
                .title(taskRequestForm.getTitle())
                .context(taskRequestForm.getContext())
                .fromDate(taskRequestForm.getFromDate())
                .toDate(taskRequestForm.getToDate())
                .payment(taskRequestForm.getPayment())
                .cutNumber(taskRequestForm.getCutNumber())
                .categoryList(taskRequestForm.getCategoryList()) // 추후 Category Repository에서 조회하여 기입하자
                .requestMember(advertiser)
                .build();

        taskRepository.saveTaskHistory(taskHistory);
    }

    @Transactional
    public void rejectTask(TaskRejectForm taskRejectForm, Long requestMemberId) {
        Member member = memberRepository.findMemberById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskRejectForm.getTaskNo());

        checkTaskValidation(member, task);

        if (TaskStatus.REJECT.equals(task.getTaskStatus()) || TaskStatus.ACCEPT.equals(task.getTaskStatus()) || TaskStatus.COMPLETE.equals(task.getTaskStatus())) {
            throw new IllegalStateException("현재 취소할 수 없는 상태입니다 : " + task.getTaskStatus());
        }

        task.changeTaskStatus(TaskStatus.REJECT);

    }

    @Transactional
    public void negotiateTask(TaskNegotiateForm taskNegotiateForm, Long requestMemberId) {
        Member requestMember = memberRepository.findMemberById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskNegotiateForm.getTaskNo());

        checkTaskValidation(requestMember, task);

        if (TaskStatus.REJECT.equals(task.getTaskStatus()) || TaskStatus.ACCEPT.equals(task.getTaskStatus()) || TaskStatus.COMPLETE.equals(task.getTaskStatus())) {
            throw new IllegalStateException("현재 취소할 수 없는 상태입니다 : " + task.getTaskStatus());
        }

        task.changeTaskStatus(TaskStatus.NEGOTIATION);

        List<TaskHistory> taskHistoryList = taskRepository.findAllTaskHistoryListByTaskNo(task);
        Integer seq = taskHistoryList.get(taskHistoryList.size() - 1).getSeq() + 1;

        TaskHistory taskHistory = TaskHistory.builder()
                .task(task)
                .seq(seq)
                .context(taskNegotiateForm.getContext())
                .fromDate(taskNegotiateForm.getFromDate())
                .toDate(taskNegotiateForm.getToDate())
                .payment(taskNegotiateForm.getPayment())
                .cutNumber(taskNegotiateForm.getCutNumber())
                .categoryList(taskNegotiateForm.getCategoryList()) // 추후 Category Repository에서 조회하여 기입하자
                .requestMember(requestMember)
                .build();

        taskRepository.saveTaskHistory(taskHistory);

    }

    @Transactional
    public void acceptTask(TaskAcceptForm taskAcceptForm, Long requestMemberId) {
        Member member = memberRepository.findMemberById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskAcceptForm.getTaskNo());

        checkTaskValidation(member, task);

        if(!MemberType.AUTHOR.equals(member.getMemberType())){
            throw new IllegalStateException("작업수락은 작가만 할 수 있습니다");
        }

        if (TaskStatus.REJECT.equals(task.getTaskStatus()) || TaskStatus.ACCEPT.equals(task.getTaskStatus()) || TaskStatus.COMPLETE.equals(task.getTaskStatus())) {
            throw new IllegalStateException("현재 수락할 수 없는 상태입니다 : " + task.getTaskStatus());
        }

        List<TaskHistory> taskHistoryList = taskRepository.findAllTaskHistoryListByTaskNo(task);

        if(!taskHistoryList.isEmpty()){
            task.acceptTask(taskHistoryList.get(taskHistoryList.size() - 1));
        } else{
            throw new IllegalStateException("작업 요청 이력이 존재하지 않습니다");
        }

    }

    public List<TaskHistory> getTaskHistoryList(Long taskNo, Long requestMemberId) {
        Member member = memberRepository.findMemberById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskNo);

        checkTaskValidation(member, task);

        return taskRepository.findAllTaskHistoryListByTaskNo(task);
    }

    private void checkTaskValidation(Member member, Task task) {
        // Validation Check

        // 요청자 존재여부
        if (member == null) {
            throw new IllegalStateException("유효하지 않은 사용자 요청");
        }

        // Task 존재여부
        if (task == null) {
            throw new IllegalStateException("존재하지 않는 작업내역입니다");
        }

        // 요청자가 Task와 관련되어 있는지 체크 (요청자 = 작가 or 광고주)
        if (MemberType.AUTHOR.equals(member.getMemberType())) {
            if (!member.equals(task.getAuthor())) {
                throw new IllegalStateException("사용자의 작업내역이 아닙니다");
            }
        } else if (MemberType.ADVERTISER.equals(member.getMemberType())) {
            if (!member.equals(task.getAdvertiser())) {
                throw new IllegalStateException("사용자의 작업내역이 아닙니다");
            }
        }
    }
}

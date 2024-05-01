package kr.co.yourplanet.online.business.task.service;

import kr.co.yourplanet.core.entity.task.Task;
import kr.co.yourplanet.core.entity.task.TaskHistory;
import kr.co.yourplanet.online.business.task.dto.request.TaskAcceptForm;
import kr.co.yourplanet.online.business.task.dto.request.TaskNegotiateForm;
import kr.co.yourplanet.online.business.task.dto.request.TaskRejectForm;
import kr.co.yourplanet.online.business.task.dto.request.TaskRequestForm;
import kr.co.yourplanet.online.business.task.repository.TaskRepository;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.core.enums.TaskStatus;
import kr.co.yourplanet.online.common.exception.BusinessException;
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
    public void requestTask(TaskRequestForm taskRequestForm, Long sponsorId) {
        Member sponsor = memberRepository.getById(sponsorId);
        Member author = memberRepository.getById(taskRequestForm.getAuthorId());

        if (sponsor == null || !MemberType.SPONSOR.equals(sponsor.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 광고주 정보입니다.", false);
        }

        if (author == null || !MemberType.AUTHOR.equals(author.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 작가 정보입니다.", false);
        }

        Task task = Task.builder()
                .author(author)
                .sponsor(sponsor)
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
                .requestMember(sponsor)
                .build();

        taskRepository.saveTaskHistory(taskHistory);
    }

    @Transactional
    public void rejectTask(TaskRejectForm taskRejectForm, Long requestMemberId) {
        Member member = memberRepository.getById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskRejectForm.getTaskNo());

        checkTaskValidation(member, task);

        if (TaskStatus.REJECT.equals(task.getTaskStatus()) || TaskStatus.ACCEPT.equals(task.getTaskStatus()) || TaskStatus.COMPLETE.equals(task.getTaskStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST,"현재 취소할 수 없는 상태입니다 : " + task.getTaskStatus(), false);
        }

        task.changeTaskStatus(TaskStatus.REJECT);

    }

    @Transactional
    public void negotiateTask(TaskNegotiateForm taskNegotiateForm, Long requestMemberId) {
        Member requestMember = memberRepository.getById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskNegotiateForm.getTaskNo());

        checkTaskValidation(requestMember, task);

        if (TaskStatus.REJECT.equals(task.getTaskStatus()) || TaskStatus.ACCEPT.equals(task.getTaskStatus()) || TaskStatus.COMPLETE.equals(task.getTaskStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 취소할 수 없는 상태입니다 : " + task.getTaskStatus(), false);
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
        Member member = memberRepository.getById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskAcceptForm.getTaskNo());

        checkTaskValidation(member, task);

        if(!MemberType.AUTHOR.equals(member.getMemberType())){
            throw new BusinessException(StatusCode.BAD_REQUEST, "작업수락은 작가만 할 수 있습니다", false);
        }

        if (TaskStatus.REJECT.equals(task.getTaskStatus()) || TaskStatus.ACCEPT.equals(task.getTaskStatus()) || TaskStatus.COMPLETE.equals(task.getTaskStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 수락할 수 없는 상태입니다 : " + task.getTaskStatus(), false);
        }

        List<TaskHistory> taskHistoryList = taskRepository.findAllTaskHistoryListByTaskNo(task);

        if(!taskHistoryList.isEmpty()){
            task.acceptTask(taskHistoryList.get(taskHistoryList.size() - 1));
        } else{
            throw new BusinessException(StatusCode.NOT_FOUND, "작업 요청 이력이 존재하지 않습니다", false);
        }

    }

    public List<TaskHistory> getTaskHistoryList(Long taskNo, Long requestMemberId) {
        Member member = memberRepository.getById(requestMemberId);
        Task task = taskRepository.findTaskByTaskNo(taskNo);

        checkTaskValidation(member, task);

        return taskRepository.findAllTaskHistoryListByTaskNo(task);
    }

    private void checkTaskValidation(Member member, Task task) {
        // Validation Check

        // 요청자 존재여부
        if (member == null) {
            throw new BusinessException(StatusCode.UNAUTHORIZED, "유효하지 않은 사용자 요청입니다.", false);
        }

        // Task 존재여부
        if (task == null) {
            throw new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 작업내역입니다", false);
        }

        // 요청자가 Task와 관련되어 있는지 체크 (요청자 = 작가 or 광고주)
        if (MemberType.AUTHOR.equals(member.getMemberType())) {
            if (!member.equals(task.getAuthor())) {
                throw new BusinessException(StatusCode.UNAUTHORIZED, "사용자의 작업내역이 아닙니다", false);
            }
        } else if (MemberType.SPONSOR.equals(member.getMemberType()) && !member.equals(task.getSponsor())) {
            throw new BusinessException(StatusCode.UNAUTHORIZED, "사용자의 작업내역이 아닙니다", false);
        }
    }
}

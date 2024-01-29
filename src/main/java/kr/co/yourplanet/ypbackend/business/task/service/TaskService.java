package kr.co.yourplanet.ypbackend.business.task.service;

import kr.co.yourplanet.ypbackend.business.task.domain.Task;
import kr.co.yourplanet.ypbackend.business.task.domain.TaskHistory;
import kr.co.yourplanet.ypbackend.business.task.dto.TaskRejectForm;
import kr.co.yourplanet.ypbackend.business.task.dto.TaskRequestForm;
import kr.co.yourplanet.ypbackend.business.task.repository.TaskRepository;
import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .requestContext(taskRequestForm.getRequsetContext())
                .fromDate(taskRequestForm.getFromDate())
                .toDate(taskRequestForm.getToDate())
                .payment(taskRequestForm.getPayment())
                .cutNumber(taskRequestForm.getCutNumber())
                //.category(taskRequestForm.getCategory())
                .requestMember(advertiser)
                .build();

        taskRepository.saveTaskHistory(taskHistory);
    }

    @Transactional
    public void rejectTask(TaskRejectForm taskRejectForm, Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        Task task = taskRepository.findTaskByTaskNo(taskRejectForm.getTaskNo());

        // Validation Check
        if (member == null) {
            throw new IllegalStateException("유효하지 않은 사용자 요청");
        }

        if (task == null) {
            throw new IllegalStateException("존재하지 않는 작업내역입니다");
        }

        if (MemberType.AUTHOR.equals(member.getMemberType())) {
            if (!member.equals(task.getAuthor())) {
                throw new IllegalStateException("사용자의 작업내역이 아닙니다");
            }
        } else if (MemberType.ADVERTISER.equals(member.getMemberType())) {
            if (!member.equals(task.getAdvertiser())) {
                throw new IllegalStateException("사용자의 작업내역이 아닙니다");
            }
        }

        if (TaskStatus.REJECT.equals(task.getTaskStatus()) || TaskStatus.ACCEPT.equals(task.getTaskStatus()) || TaskStatus.COMPLETE.equals(task.getTaskStatus())) {
            throw new IllegalStateException("현재 취소할 수 없는 상태입니다 : " + task.getTaskStatus().toString());
        }

        task.changeTaskStatus(TaskStatus.REJECT);

    }
}

package kr.co.yourplanet.ypbackend.business.task.service;

import kr.co.yourplanet.ypbackend.business.task.domain.Task;
import kr.co.yourplanet.ypbackend.business.task.dto.TaskRequestForm;
import kr.co.yourplanet.ypbackend.business.task.repository.TaskRepository;
import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.business.user.repository.MemberRepository;
import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .requestContext(taskRequestForm.getRequsetContext())
                .fromDate(taskRequestForm.getFromDate())
                .toDate(taskRequestForm.getToDate())
                .taskStatus(TaskStatus.REQUEST)
                .build();

        taskRepository.saveTask(task);
    }
}

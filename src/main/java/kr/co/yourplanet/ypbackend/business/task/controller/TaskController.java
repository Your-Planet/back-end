package kr.co.yourplanet.ypbackend.business.task.controller;

import kr.co.yourplanet.ypbackend.business.task.domain.TaskHistory;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskAcceptForm;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskNegotiateForm;
import kr.co.yourplanet.ypbackend.business.task.dto.response.TaskHistories;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskRejectForm;
import kr.co.yourplanet.ypbackend.business.task.dto.request.TaskRequestForm;
import kr.co.yourplanet.ypbackend.business.task.service.TaskService;
import kr.co.yourplanet.ypbackend.jwt.JwtPrincipal;
import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task/request")
    public ResponseEntity<ResponseForm<String>> requestNewTask(@Valid @RequestBody TaskRequestForm taskRequestForm, @AuthenticationPrincipal JwtPrincipal principal) {

        taskService.requestTask(taskRequestForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, "", "OK"), HttpStatus.OK);
    }

    @PostMapping("/task/reject")
    public ResponseEntity<ResponseForm<String>> rejectTask(@Valid @RequestBody TaskRejectForm taskRejectForm, @AuthenticationPrincipal JwtPrincipal principal) {

        taskService.rejectTask(taskRejectForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, "", "OK"), HttpStatus.OK);
    }

    @PostMapping("/task/negotiate")
    public ResponseEntity<ResponseForm<String>> negotiateTask(@Valid @RequestBody TaskNegotiateForm taskNegotiateForm, @AuthenticationPrincipal JwtPrincipal principal) {

        taskService.negotiateTask(taskNegotiateForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, "", "OK"), HttpStatus.OK);
    }

    @PostMapping("/task/accept")
    public ResponseEntity<ResponseForm<String>> acceptTask(@Valid @RequestBody TaskAcceptForm taskAcceptForm, @AuthenticationPrincipal JwtPrincipal principal) {

        taskService.acceptTask(taskAcceptForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, "", "OK"), HttpStatus.OK);
    }

    @GetMapping("/task/history")
    public ResponseEntity<ResponseForm<List<TaskHistories>>> getTaskHistoryList(@RequestParam("taskNo") Long taskNo, @AuthenticationPrincipal JwtPrincipal principal) {
        List<TaskHistories> taskHistoriesList = new ArrayList<>();

        List<TaskHistory> taskHistoryList = taskService.getTaskHistoryList(taskNo, principal.getId());

        for (TaskHistory taskHistory : taskHistoryList) {
            TaskHistories taskHistories = TaskHistories.builder()
                    .taskNo(taskHistory.getTask().getTaskNo())
                    .seq(taskHistory.getSeq())
                    .requestContext(taskHistory.getContext())
                    .fromDate(taskHistory.getFromDate())
                    .toDate(taskHistory.getToDate())
                    .payment(taskHistory.getPayment())
                    .cutNumber(taskHistory.getCutNumber())
                    .categoryList(taskHistory.getCategoryList())
                    .requestMemberType(taskHistory.getRequestMember().getMemberType())
                    .build();

            taskHistoriesList.add(taskHistories);
        }

        ResponseForm<List<TaskHistories>> responseForm = new ResponseForm<>(StatusCode.OK, "", taskHistoriesList);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }
}

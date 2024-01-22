package kr.co.yourplanet.ypbackend.business.task.controller;

import kr.co.yourplanet.ypbackend.business.task.dto.TaskRequestForm;
import kr.co.yourplanet.ypbackend.business.task.service.TaskService;
import kr.co.yourplanet.ypbackend.common.JwtPrincipal;
import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task/request")
    public ResponseForm<String> requestTask(@RequestBody TaskRequestForm taskRequestForm, @AuthenticationPrincipal JwtPrincipal principal) {

        taskService.requestTask(taskRequestForm, principal.getId());

        return new ResponseForm<>(StatusCode.OK, "", "OK");
    }
}

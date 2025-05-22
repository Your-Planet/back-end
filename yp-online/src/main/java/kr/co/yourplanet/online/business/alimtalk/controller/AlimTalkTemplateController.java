package kr.co.yourplanet.online.business.alimtalk.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.alimtalk.dto.AlimTalkTemplateForm;
import kr.co.yourplanet.online.business.alimtalk.service.AlimTalkTemplateService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;

@Tag(name = "AlimTalkTemplate", description = "알림톡 템플릿 API")
@RestController
@RequiredArgsConstructor
public class AlimTalkTemplateController {

    private final AlimTalkTemplateService alimTalkTemplateService;

    @Operation(summary = "알림톡 템플릿 등록/수정")
    @PostMapping("/alim-talk/template")
    public ResponseEntity<ResponseForm<Void>> saveAlimTalkTemplate(
        @AuthenticationPrincipal JwtPrincipal principal,
        @Valid @RequestBody AlimTalkTemplateForm request
    ) {
        alimTalkTemplateService.mergeAlimTalkTemplate(request);

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @Operation(summary = "알림톡 템플릿 조회")
    @GetMapping("/alim-talk/template")
    public ResponseEntity<ResponseForm<List<AlimTalkTemplateForm>>> saveAlimTalkTemplate(
        @AuthenticationPrincipal JwtPrincipal principal
    ) {
        List<AlimTalkTemplateForm> allAlimTalkTemplates = alimTalkTemplateService.getAllAlimTalkTemplates();

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, allAlimTalkTemplates), HttpStatus.OK);
    }

}

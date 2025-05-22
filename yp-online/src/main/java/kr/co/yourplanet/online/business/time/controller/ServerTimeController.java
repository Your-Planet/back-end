package kr.co.yourplanet.online.business.time.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.time.dto.ServerTimeResponse;
import kr.co.yourplanet.online.common.ResponseForm;

@Tag(name = "ServerTime", description = "서버 시간 API")
@RestController
public class ServerTimeController {

    @Operation(summary = "서버 시간 조회")
    @GetMapping(value = "/api/time")
    public ResponseEntity<ResponseForm<ServerTimeResponse>> getServerTime() {
        LocalDateTime now = LocalDateTime.now();

        return ResponseEntity.ok(new ResponseForm<>(StatusCode.OK, new ServerTimeResponse(now)));
    }

}
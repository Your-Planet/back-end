package kr.co.yourplanet.online.business.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.file.dto.UploadFilesForm;
import kr.co.yourplanet.online.business.file.dto.UploadUrlInfo;
import kr.co.yourplanet.online.business.file.service.FileUploadService;
import kr.co.yourplanet.online.business.file.service.FileUrlService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import kr.co.yourplanet.online.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Tag(name = "File", description = "파일 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileProperties fileProperties;
    private final FileUrlService fileUrlService;

    @GetMapping(value = "/profile/{fileName:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getProfileImage(@PathVariable String fileName) {
        Path file = Paths.get(fileProperties.getProfilePath()).resolve(fileName);

        try {
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new BusinessException(StatusCode.NOT_FOUND, "파일을 찾을 수 없습니다.", false);
            }

            return new ResponseEntity<>(resource, HttpStatus.OK);

        } catch (MalformedURLException e) {
            log.error("MalformedURLException URI:{}. {} ", file.toUri(), e.getMessage());
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/project/reference/{fileName:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getProjectReferenceFile(@PathVariable String fileName) {
        Path file = Paths.get(fileProperties.getProjectReferenceFilePath()).resolve(fileName);

        try {
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new BusinessException(StatusCode.NOT_FOUND, "파일을 찾을 수 없습니다.", false);
            }

            return new ResponseEntity<>(resource, HttpStatus.OK);

        } catch (MalformedURLException e) {
            log.error("MalformedURLException URI:{}. {} ", file.toUri(), e.getMessage());
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "upload url 생성", description = "요청 당 최대 5개")
    @PostMapping("/upload-urls")
    public ResponseEntity<ResponseForm<List<UploadUrlInfo>>> generateUploadUrls(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody UploadFilesForm request
    ) {
        List<UploadUrlInfo> response = fileUrlService.getUploadUrls(principal.getId(), request);

        return new ResponseEntity<>(
                new ResponseForm<>(StatusCode.CREATED, response),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "download url 테스트 API")
    @PostMapping("/download-url")
    public ResponseEntity<ResponseForm<String>> generateDownloadUrl(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestParam Long fileId
    ) {
        String url = fileUrlService.getDownloadUrl(fileId, principal.getId());

        return new ResponseEntity<>(
                new ResponseForm<>(StatusCode.CREATED, url),
                HttpStatus.CREATED
        );
    }
}
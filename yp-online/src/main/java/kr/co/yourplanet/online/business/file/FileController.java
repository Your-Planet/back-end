package kr.co.yourplanet.online.business.file;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileProperties fileProperties;

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
}

package kr.co.yourplanet.online.common.util;

import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileManageUtil {

    private final FileProperties fileProperties;

    public FileUploadResult uploadFile(MultipartFile multipartFile, FileType fileType) {
        validateFile(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        String randomFileName = generateRandomFileName(originalFilename);
        Path fileAbsolutePath = generateFileAbsolutePath(fileType, randomFileName);
        String fileUrl = generateFileUrl(fileType, randomFileName);

        try {
            Files.write(fileAbsolutePath, multipartFile.getBytes());

            return FileUploadResult.builder()
                    .fileName(randomFileName)
                    .filePath(fileAbsolutePath.toString())
                    .fileUrl(fileUrl)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "파일 저장 중 오류가 발생하였습니다.", false);
        }
    }

    public void deleteFile(String fileFullPath) {
        Path path = Paths.get(fileFullPath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("파일 삭제 오류 : " + path.toString());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "빈 파일은 저장할 수 없습니다.", false);
        }
    }

    private Path generateFileAbsolutePath(FileType fileType, String fileName) {
        if (FileType.PROFILE_IMAGE.equals(fileType)) {
            return Paths.get(fileProperties.getProfilePath()).resolve(fileName);
        } else {
            throw new BusinessException(StatusCode.BAD_REQUEST, "지원하지 않는 파일 타입입니다.", false);
        }
    }

    private String generateFileUrl(FileType fileType, String fileName) {
        String propertyUrl = "";
        if (FileType.PROFILE_IMAGE.equals(fileType)) {
            propertyUrl += fileProperties.getProfileUrl();
        } else {
            throw new BusinessException(StatusCode.BAD_REQUEST, "지원하지 않는 파일 타입입니다.", false);
        }

        return propertyUrl + fileName;
    }

    private String generateRandomFileName(String fileName) {
        return UUID.randomUUID().toString() + "-" + fileName;
    }

}
package kr.co.yourplanet.online.common.util;

import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileManageUtil {

    private static final List<String> IMAGE_VALID_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static long MAX_IMAGE_SIZE = 5 * 1024 * 1024;

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

    public void validateImageFile(MultipartFile file) {

        validateFile(file);

        String fileName = file.getOriginalFilename();
        if (!StringUtils.hasText(fileName)) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "파일명이 존재하지 않습니다.", false);
        }

        // 이미지 파일 확장자 검사
        if (!IMAGE_VALID_EXTENSIONS.contains(getFileExtension(fileName))) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 파일 확장자입니다.", false);
        }

        // 이미지 파일 최대 용량 확인
        if (MAX_IMAGE_SIZE < file.getSize()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "이미지는 최대 5MB까지 업로드 가능합니다.", false);
        }

    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "빈 파일은 저장할 수 없습니다.", false);
        }
    }

    private String getFileExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        return parts.length > 1 ? parts[parts.length - 1] : "";
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
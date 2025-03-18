package kr.co.yourplanet.online.business.file.service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.yourplanet.core.enums.FilePathKey;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.file.adapter.StorageAdapter;
import kr.co.yourplanet.online.business.file.dto.PresignedUrlsForm;
import kr.co.yourplanet.online.business.file.dto.PresignedUrlsResponse;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final StorageAdapter storageAdapter;
    private final FileManageUtil fileManageUtil;

    public URL uploadImage(MultipartFile imageFile, FileType fileType, String fileKey) {
        fileManageUtil.validateFile(imageFile, fileType);
        return storageAdapter.upload(imageFile, fileKey);
    }

    public PresignedUrlsResponse generatePresignedUrls(Map<FilePathKey, String> metadata, PresignedUrlsForm presignedUrlsForm) {
        FileType fileType = presignedUrlsForm.fileType();
        Map<String, MediaType> fileMediaTypes = mapFileMediaTypes(presignedUrlsForm.fileNames(), fileType);
        List<String> presignedUrls = createPresignedUrls(fileType, metadata, fileMediaTypes);

        return PresignedUrlsResponse.builder()
                .presignedUrls(presignedUrls)
                .build();
    }

    private Map<String, MediaType> mapFileMediaTypes(List<String> fileNames, FileType fileType) {
        return fileNames.stream()
                .collect(Collectors.toMap(
                        fileName -> {
                            fileManageUtil.validateFileName(fileName);
                            fileManageUtil.validateFileExtension(fileType, fileName);
                            return fileName;
                        },
                        fileName -> MediaTypeFactory.getMediaType(fileName)
                                .orElseThrow(() -> new BusinessException(StatusCode.UNSUPPORTED_MEDIA_TYPE))));
    }

    private List<String> createPresignedUrls(FileType fileType, Map<FilePathKey, String> metadata, Map<String, MediaType> fileMediaTypes) {
        return fileMediaTypes.entrySet().stream()
                .map(entry -> {
                    String fileName = entry.getKey();
                    String fileKey = fileType.formatPath(metadata);
                    return storageAdapter.generatePresignedUrl(fileName, fileKey, entry.getValue()).toString();
                })
                .toList();
    }
}

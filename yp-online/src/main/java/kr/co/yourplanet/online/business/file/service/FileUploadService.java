package kr.co.yourplanet.online.business.file.service;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.online.business.file.adapter.StorageAdapter;
import kr.co.yourplanet.online.business.file.dto.PresignedUrlsForm;
import kr.co.yourplanet.online.business.file.dto.PresignedUrlsResponse;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FileUploadService {
    public static final String MEMBER_ID = "memberId";

    private final StorageAdapter storageAdapter;
    private final FileManageUtil fileManageUtil;

    public URL uploadImageFile(MultipartFile imageFile, FileType fileType, String fileKey) {
        fileManageUtil.validateFile(imageFile, fileType);

        return storageAdapter.upload(imageFile, fileKey);
    }

    public PresignedUrlsResponse getPresignedUrls(Map<String, String> metadata, PresignedUrlsForm form) {
        FileType fileType = form.fileType();
        List<String> fileKeys = form.fileNames().stream()
                .map(fileName -> fileManageUtil.generateFileUrl(fileType, fileName, metadata))
                .toList();

        // TODO: 비동기로 전환
        List<String> urls = storageAdapter.generatePresignedUrls(fileKeys)
                .stream()
                .map(URL::toString)
                .toList();

        return PresignedUrlsResponse.builder()
                .presignedUrls(urls)
                .build();
    }
}

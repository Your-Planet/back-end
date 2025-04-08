package kr.co.yourplanet.online.business.file.service;

import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.online.business.file.adapter.StorageAdapter;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final StorageAdapter storageAdapter;
    private final FileManageUtil fileManageUtil;

    public URL upload(MultipartFile imageFile, FileType fileType, String fileKey) {
        fileManageUtil.validateFile(imageFile, fileType);
        return storageAdapter.upload(imageFile, fileKey);
    }
}

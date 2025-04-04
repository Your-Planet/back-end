package kr.co.yourplanet.online.business.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.model.FileMetadata;
import kr.co.yourplanet.online.business.file.adapter.StorageAdapter;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FileQueryService {

    private final StorageAdapter storageAdapter;

    public FileMetadata getFileMetaData(String fileUrl) {
        return storageAdapter.getMetadata(fileUrl);
    }
}

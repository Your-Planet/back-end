package kr.co.yourplanet.online.business.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.online.business.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMetadataRepository fileMetadataRepository;

    public long save(FileMetadata fileMetadata) {
        return fileMetadataRepository.save(fileMetadata).getId();
    }
}

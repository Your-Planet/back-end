package kr.co.yourplanet.online.business.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.online.business.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileQueryService fileQueryService;
    private final FileValidationService fileValidationService;

    private final FileMetadataRepository fileMetadataRepository;

    public long save(FileMetadata fileMetadata) {
        return fileMetadataRepository.save(fileMetadata).getId();
    }

    public void completeUpload(long fileId, long uploaderId, Long targetId, FileType fileType) {
        fileValidationService.checkMatchingFile(fileId, uploaderId, fileType);

        FileMetadata file = fileQueryService.getById(fileId);
        file.linkTarget(targetId);

        fileMetadataRepository.save(file);
    }

    public void replace(long existId, long newId, long uploaderId, Long targetId, FileType fileType) {
        // 삭제 전 외래키 해제가 선행되어야 함
        delete(existId);
        completeUpload(newId, uploaderId, targetId, fileType);
    }

    public void delete(long fileId) {
        FileMetadata file = fileQueryService.getById(fileId);
        fileMetadataRepository.delete(file);
    }
}

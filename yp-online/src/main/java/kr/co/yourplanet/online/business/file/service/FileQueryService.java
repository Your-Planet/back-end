package kr.co.yourplanet.online.business.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.file.dto.FileMetadataInfo;
import kr.co.yourplanet.online.business.file.repository.FileMetadataRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FileQueryService {

    private final FileMetadataRepository fileMetadataRepository;

    public FileMetadataInfo getFileMetaDataInfo(long fileId) {
        FileMetadata file = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당하는 파일을 찾을 수 없습니다.", false));

        return FileMetadataInfo.builder()
                .fileName(file.getOriginalName())
                .bytes(file.getSize())
                .build();
    }

    public FileMetadata getFileMetaData(long fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당하는 파일을 찾을 수 없습니다.", false));
    }
}

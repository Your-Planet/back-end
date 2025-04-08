package kr.co.yourplanet.online.business.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FileValidationService {

    private final FileQueryService fileQueryService;

    public void checkUploaded(long fileId) {
        FileMetadata file = fileQueryService.getFileMetaData(fileId);

        if (Boolean.FALSE.equals(file.getUploaded())) {
            throw new BusinessException(StatusCode.CONFLICT, "업로드 되지 않은 파일입니다.", true);
        }
    }

    public void checkPermission(long fileId, long requesterId) {
        FileMetadata file = fileQueryService.getFileMetaData(fileId);

        if (!file.canAccessBy(requesterId)) {
            throw new BusinessException(StatusCode.FORBIDDEN, "해당 파일에 접근할 수 없습니다.", false);
        }
    }

    public void checkFileType(long fileId, FileType fileType) {
        FileMetadata file = fileQueryService.getFileMetaData(fileId);

        if (!file.getFileType().equals(fileType)) {
            throw new BusinessException(StatusCode.CONFLICT, "파일 타입이 일치하지 않습니다.", false);
        }
    }

    public void checkMatchingFile(long fileId, long uploaderId, FileType fileType) {
        checkUploaded(fileId);
        checkPermission(fileId, uploaderId);
        checkFileType(fileId, fileType);
    }
}

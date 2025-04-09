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

    public void checkMatchingFile(long fileId, long uploaderId, FileType fileType) {
        checkUploaded(fileId);
        checkUploader(fileId, uploaderId);
        checkFileType(fileId, fileType);
        checkAlreadyLinked(fileId);
    }

    public void checkUploaded(long fileId) {
        FileMetadata file = fileQueryService.getById(fileId);

        if (Boolean.FALSE.equals(file.getUploaded())) {
            throw new BusinessException(StatusCode.CONFLICT, "업로드 되지 않은 파일입니다.", true);
        }
    }

    public void checkUploader(long fileId, long requesterId) {
        FileMetadata file = fileQueryService.getById(fileId);

        if (!file.isUploader(requesterId)) {
            throw new BusinessException(StatusCode.FORBIDDEN, "해당 파일에 접근할 수 없습니다.", false);
        }
    }

    public void checkSecret(long fileId) {
        FileMetadata file = fileQueryService.getById(fileId);

        if (file.isSecret()) {
            throw new BusinessException(StatusCode.FORBIDDEN, "열람할 수 없는 파일 입니다.", false);
        }
    }

    public void checkFileType(long fileId, FileType fileType) {
        FileMetadata file = fileQueryService.getById(fileId);

        if (!file.getFileType().equals(fileType)) {
            throw new BusinessException(StatusCode.CONFLICT, "파일 타입이 일치하지 않습니다.", false);
        }
    }

    public void checkAlreadyLinked(long fileId) {
        FileMetadata file = fileQueryService.getById(fileId);

        if (file.getReferenceId() != null) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 연결된 파일입니다.", true);
        }
    }
}

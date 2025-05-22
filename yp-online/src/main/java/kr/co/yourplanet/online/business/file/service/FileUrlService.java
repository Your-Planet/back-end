package kr.co.yourplanet.online.business.file.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.file.adapter.StorageAdapter;
import kr.co.yourplanet.online.business.file.dto.UploadFilesForm;
import kr.co.yourplanet.online.business.file.dto.UploadUrlInfo;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FileUrlService {

    private final FileService fileService;
    private final FileQueryService fileQueryService;
    private final FileValidationService fileValidationService;
    private final MemberQueryService memberQueryService;

    private final StorageAdapter storageAdapter;
    private final FileManageUtil fileManageUtil;

    @Value("${spring.cloud.config.server.aws.s3.expire-time.upload}")
    private long uploadExpireTime;

    @Value("${spring.cloud.config.server.aws.s3.expire-time.download}")
    private long downloadExpireTime;

    public List<UploadUrlInfo> getUploadUrls(Long memberId, UploadFilesForm form) {
        FileType fileType = form.fileType();
        Member member = memberQueryService.getById(memberId);

        return form.fileNames().stream()
                .map(name -> getUploadUrl(member, fileType, name))
                .toList();
    }

    private UploadUrlInfo getUploadUrl(Member member, FileType fileType, String fileName) {
        fileManageUtil.validateFileName(fileName);
        fileManageUtil.validateFileExtension(fileType, fileName);

        // 업로드 url 생성
        MediaType mediaType = MediaTypeFactory.getMediaType(fileName)
                .orElseThrow(() -> new BusinessException(StatusCode.UNSUPPORTED_MEDIA_TYPE));
        String fileKey = fileManageUtil.generateFileKey(fileType, fileName);
        String url = storageAdapter.getUploadUrl(fileKey, mediaType, uploadExpireTime).toString();

        // 업로드 예약된 파일 메타데이터 생성
        FileMetadata fileMetadata = FileMetadata.createReserved(member, fileKey, fileName, fileManageUtil.getFileExtension(fileName), fileType);
        long fileId = fileService.save(fileMetadata);

        return UploadUrlInfo.builder()
                .fileId(fileId)
                .url(url)
                .build();
    }

    public String getDownloadUrl(long fileId, long requesterId) {
        fileValidationService.checkUploaded(fileId);
        fileValidationService.checkUploader(fileId, requesterId);
        FileMetadata file = fileQueryService.getById(fileId);

        return storageAdapter.getDownloadUrl(file.getKey(), downloadExpireTime).toString();
    }

    public String getPublicUrl(long fileId) {
        fileValidationService.checkUploaded(fileId);
        fileValidationService.checkSecret(fileId);

        FileMetadata file = fileQueryService.getById(fileId);
        return storageAdapter.getPublicUrl(file.getKey()).toString();
    }

    public String getSecureUrl(long fileId, long requesterId) {
        // TODO: 기획에 따른 추가 제약사항 + 권한 확인
        fileValidationService.checkUploaded(fileId);
        fileValidationService.checkSecret(fileId);

        FileMetadata file = fileQueryService.getById(fileId);
        return storageAdapter.getSecureUrl(file.getKey(), 3600).toString();
    }
}

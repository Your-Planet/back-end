package kr.co.yourplanet.online.business.file.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.online.business.file.adapter.StorageAdapter;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileService fileService;
    private final MemberQueryService memberQueryService;

    private final StorageAdapter storageAdapter;
    private final FileManageUtil fileManageUtil;

    public FileMetadata upload(MultipartFile imageFile, FileType fileType, long uploaderId) {
        fileManageUtil.validateFile(imageFile, fileType);

        Member uploader = memberQueryService.getById(uploaderId);
        String fileName = imageFile.getOriginalFilename();
        String fileKey = fileManageUtil.generateFileKey(fileType, fileName);
        String ext = fileManageUtil.getFileExtension(fileName);

        FileMetadata file = FileMetadata.createUploaded(uploader, fileKey, fileName, ext, fileType);
        fileService.save(file);
        storageAdapter.upload(imageFile, fileKey);

        return file;
    }
}

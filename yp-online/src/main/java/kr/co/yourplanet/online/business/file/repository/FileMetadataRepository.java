package kr.co.yourplanet.online.business.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.core.enums.FileType;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    // 연결된 참조 값으로 조회
    List<FileMetadata> findAllByReferenceIdAndFileType(Long referenceId, FileType fileType);
}

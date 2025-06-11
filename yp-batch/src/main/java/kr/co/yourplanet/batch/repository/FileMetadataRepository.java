package kr.co.yourplanet.batch.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.file.FileMetadata;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    List<FileMetadata> findByUploadedFalseAndCreateDateBefore(LocalDateTime dateTime);
}

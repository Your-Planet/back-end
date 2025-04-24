package kr.co.yourplanet.online.business.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.file.FileMetadata;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}

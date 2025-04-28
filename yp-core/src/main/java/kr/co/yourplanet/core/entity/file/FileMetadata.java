package kr.co.yourplanet.core.entity.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.FileType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FileMetadata extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_metadata_seq")
    @SequenceGenerator(name = "file_metadata_seq", sequenceName = "file_metadata_seq", allocationSize = 10)
    private Long id;

    @Column(name = "file_key", nullable = false, length = 100)
    private String key;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "file_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(nullable = false, length = 10)
    private String extension;

    @Column(name = "size")
    private Long size;

    @Column(nullable = false)
    private Boolean uploaded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private Member uploader;

    @Column(name = "reference_id")
    private Long referenceId;

    public static FileMetadata createReserved(Member uploader, String key, String name, String extension, FileType fileType) {
        return FileMetadata.builder()
                .key(key)
                .originalName(name)
                .extension(extension)
                .uploaded(false)
                .fileType(fileType)
                .uploader(uploader)
                .build();
    }

    public static FileMetadata createUploaded(Member uploader, String key, String name, String extension, FileType fileType) {
        return FileMetadata.builder()
                .key(key)
                .originalName(name)
                .extension(extension)
                .uploaded(true)
                .fileType(fileType)
                .uploader(uploader)
                .build();
    }

    public boolean isUploader(long memberId) {
        return uploader.getId().equals(memberId);
    }

    public boolean isSecret() {
        return FileType.SETTLEMENT_FILE.equals(this.fileType);
    }

    public void linkReference(long referenceId) {
        this.referenceId = referenceId;
    }
}

package kr.co.yourplanet.core.enums;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public enum FileType {

    PROFILE_IMAGE(
            "/files/profile/member/%s",
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif")),
            List.of(FilePathKey.MEMBER_ID)
    ),
    PROJECT_REFERENCE_FILE(
            "/files/project/reference/",
            new HashSet<>(Set.of("pdf", "doc", "docx", "txt", "ppt", "pptx", "xls", "xlsx", "csv", "xml")),
            List.of()
    ),
    SETTLEMENT_FILE(
            "files/secret/settlement/member/%s",
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif", "pdf")),
            List.of(FilePathKey.MEMBER_ID)
    );

    private final String pathTemplate;
    private final Set<String> allowedExtensions;
    private final List<FilePathKey> requiredMetadata;

    FileType(String pathTemplate, Set<String> allowedExtensions, List<FilePathKey> requiredMetadata) {
        this.pathTemplate = pathTemplate;
        this.allowedExtensions = allowedExtensions;
        this.requiredMetadata = requiredMetadata;
    }

    public boolean isAllowedExtension(String extension) {
        return allowedExtensions.contains(extension);
    }

    public String formatPath(Map<FilePathKey, String> metadata) {
        if (!requiredMetadata.isEmpty() && requiredMetadata.stream().anyMatch(key -> !metadata.containsKey(key))) {
            throw new IllegalArgumentException("필수 메타데이터가 누락되었습니다.");
        }
        return String.format(pathTemplate, requiredMetadata.stream().map(metadata::get).toArray());
    }
}

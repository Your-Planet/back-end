package kr.co.yourplanet.core.enums;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public enum FileType {

    PROFILE_IMAGE(
            "files/profile/member/",
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif"))
    ),
    PROJECT_REFERENCE_FILE(
            "files/project/reference/",
            new HashSet<>(Set.of("pdf", "doc", "docx", "txt", "ppt", "pptx", "xls", "xlsx", "csv", "xml"))
    ),
    SETTLEMENT_FILE(
            "files/secret/settlement/",
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif", "pdf"))
    );

    private final String path;
    private final Set<String> allowedExtensions;

    FileType(String path, Set<String> allowedExtensions) {
        this.path = path;
        this.allowedExtensions = allowedExtensions;
    }

    public boolean isAllowedExtension(String extension) {
        return allowedExtensions.contains(extension);
    }
}

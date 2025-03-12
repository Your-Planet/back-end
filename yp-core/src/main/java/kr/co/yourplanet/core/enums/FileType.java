package kr.co.yourplanet.core.enums;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public enum FileType {

    PROFILE_IMAGE(
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif"))
    ),
    PROJECT_REFERENCE_FILE(
            new HashSet<>(Set.of("pdf", "doc", "docx", "txt", "ppt", "pptx", "xls", "xlsx", "csv", "xml"))
    ),
    SETTLEMENT_FILE(
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif"))
    );

    private final Set<String> allowedExtensions;

    FileType(Set<String> set) {
        allowedExtensions = set;
    }

    public boolean isAllowedExtension(String extension) {
        return allowedExtensions.contains(extension);
    }
}

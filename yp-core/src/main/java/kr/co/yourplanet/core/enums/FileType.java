package kr.co.yourplanet.core.enums;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public enum FileType {

    PROFILE_IMAGE(
            "files/profile/member/",
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif")), 
            true
    ),
    PROJECT_REFERENCE_FILE(
            "files/project/reference/",
            new HashSet<>(Set.of("pdf", "doc", "docx", "txt", "ppt", "pptx", "xls", "xlsx", "csv", "xml")), 
            true
    ),
    SETTLEMENT_FILE(
            "files/secret/settlement/",
            new HashSet<>(Set.of("jpg", "jpeg", "png", "gif", "pdf")), 
            false
    );

    private final String path;
    private final Set<String> allowedExtensions;
    private final boolean viewable;

    FileType(String path, Set<String> allowedExtensions, boolean viewable) {
        this.path = path;
        this.allowedExtensions = allowedExtensions;
        this.viewable = viewable;
    }

    public boolean isAllowedExtension(String extension) {
        return allowedExtensions.contains(extension);
    }
}

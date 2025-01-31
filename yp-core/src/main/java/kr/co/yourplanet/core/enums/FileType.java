package kr.co.yourplanet.core.enums;

import java.util.HashSet;
import java.util.Set;

public enum FileType {

	PROFILE_IMAGE(new HashSet<>(Set.of("jpg", "jpeg", "png", "gif"))),
	PROJECT_REFERENCE_FILE(
		new HashSet<>(Set.of("pdf", "doc", "docx", "txt", "ppt", "pptx", "xls", "xlsx", "csv", "xml")));

	private final Set<String> allowedExtensions;

	private FileType(Set<String> set) {
		allowedExtensions = set;
	}

	public Set<String> getAllowedExtensions() {
		return allowedExtensions;
	}

	public boolean isAllowedExtension(String extension) {
		return allowedExtensions.contains(extension);
	}
}

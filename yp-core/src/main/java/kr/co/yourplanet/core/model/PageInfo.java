package kr.co.yourplanet.core.model;

import org.springframework.data.domain.Page;

public record PageInfo(
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {

    public static PageInfo from(Page<?> pages) {
        return new PageInfo(
                pages.getNumber(),
                pages.getSize(),
                pages.getTotalElements(),
                pages.getTotalPages(),
                pages.hasNext()
        );
    }
}

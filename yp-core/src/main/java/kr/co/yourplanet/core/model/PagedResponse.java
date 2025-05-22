package kr.co.yourplanet.core.model;

import java.util.List;

import org.springframework.data.domain.Page;

public record PagedResponse<T>(
        List<T> content,
        PageInfo pageInfo
) {

    public static <T> PagedResponse<T> from(Page<T> pages) {
        return new PagedResponse<>(
                pages.getContent(),
                PageInfo.from(pages)
        );
    }
}

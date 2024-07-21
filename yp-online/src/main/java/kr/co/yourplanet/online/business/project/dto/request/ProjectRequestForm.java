package kr.co.yourplanet.online.business.project.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectRequestForm {

    @NotNull
    private Long authorId;

    private String title;

    @NotBlank(message = "요청사항을 기입해주세요")
    private String context;

    private List<String> categoryList;

    private Integer cutNumber;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private Long payment;
}

package kr.co.yourplanet.ypbackend.business.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskRequestForm {

    @NotNull
    private Long authorId;

    @NotBlank(message = "요청사항을 기입해주세요")
    private String requsetContext;

    private String category;

    private Integer cutNumber;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private Long payment;
}

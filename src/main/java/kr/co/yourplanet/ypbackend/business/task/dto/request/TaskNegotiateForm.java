package kr.co.yourplanet.ypbackend.business.task.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class TaskNegotiateForm {

    @NotNull
    private Long taskNo;

    @NotBlank(message = "요청사항을 기입해주세요")
    private String requsetContext;

    private String category;

    private Integer cutNumber;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private Long payment;
}

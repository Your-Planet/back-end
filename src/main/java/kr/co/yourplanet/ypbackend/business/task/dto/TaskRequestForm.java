package kr.co.yourplanet.ypbackend.business.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskRequestForm {

    private Long authorId;

    private String requsetContext;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private long pay;
}

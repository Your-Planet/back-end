package kr.co.yourplanet.ypbackend.business.task.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TaskRejectForm {

    @NotNull
    private Long taskNo;
}

package kr.co.yourplanet.ypbackend.business.task.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TaskAcceptForm {

    @NotNull
    private Long taskNo;
}

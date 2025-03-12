package kr.co.yourplanet.online.business.project.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProjectRejectForm {

    @NotNull
    private Long id;

    private String reason;

}

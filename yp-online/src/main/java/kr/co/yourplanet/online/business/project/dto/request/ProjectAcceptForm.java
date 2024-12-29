package kr.co.yourplanet.online.business.project.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ProjectAcceptForm {

    @NotNull
    private Long id;
}

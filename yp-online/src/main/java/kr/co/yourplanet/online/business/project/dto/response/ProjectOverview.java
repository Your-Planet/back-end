package kr.co.yourplanet.online.business.project.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectOverview {
	@NotBlank
	private String sponsorName;
	@NotBlank
	private String creatorName;
	@NotBlank
	private String brandName;
	@NotBlank
	private String orderTitle;
	@NotBlank
	private String orderCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@NotNull
	private LocalDate dueDate;
	private Integer defaultPanelCount;
	private Integer additionalPanelCount;
	private Integer defaultModificationCount;
	private Integer additionalModificationCount;
	private Integer offerPrice;
}
package kr.co.yourplanet.online.business.project.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectOverview {
	private String sponsorName;
	private String creatorName;
	private String brandName;
	private String orderTitle;
	private String orderCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate dueDate;
	private Integer defaultPanelCount;
	private Integer additionalPanelCount;
	private Integer defaultModificationCount;
	private Integer additionalModificationCount;
	private Integer offerPrice;
}
package kr.co.yourplanet.online.business.project.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDetail {

	private String campaignDescription;
	private List<String> referenceUrls;
	private List<ReferenceFileInfo> referenceFiles;
	private ProjectHistoryForm latestProjectHistory;

}

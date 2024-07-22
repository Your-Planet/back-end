package kr.co.yourplanet.online.business.project.dto.response;

import kr.co.yourplanet.core.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProjectHistories {

    private Long id;
    private Integer seq;
    private Integer additionalCuts;
    private Integer modificationCount;
    private Integer additionalPostDurationMonth;
    private boolean isOriginFileRequest;
    private boolean isRefinementRequest;
    private List<LocalDate> postDates;
    private LocalDate postFromDate;
    private LocalDate postToDate;
    private LocalDate dueDate;
    private String brandName;
    private List<String> brandUrls;
    private String campaignDescription;
    private List<String> campaignUrls;
    private Integer offerPrice;
    private String requestNotes;
    private List<String> categoryList;
    private MemberType requestMemberType;
}

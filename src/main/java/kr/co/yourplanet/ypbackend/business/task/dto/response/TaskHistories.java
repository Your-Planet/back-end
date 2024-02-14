package kr.co.yourplanet.ypbackend.business.task.dto.response;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
public class TaskHistories {

    private Long taskNo;
    private Integer seq;
    private String requestContext;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long payment;
    private Integer cutNumber;
    private String categoryName;
    private MemberType requestMemberType;
}

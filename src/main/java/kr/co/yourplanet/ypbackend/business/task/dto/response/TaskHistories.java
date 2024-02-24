package kr.co.yourplanet.ypbackend.business.task.dto.response;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TaskHistories {

    private Long taskNo;
    private Integer seq;
    private String requestContext;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long payment;
    private Integer cutNumber;
    private List<String> categoryList;
    private MemberType requestMemberType;
}

package kr.co.yourplanet.core.entity.project;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.co.yourplanet.core.enums.PostDurationMonthType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class AdditionalDetail {

    @Comment("작업 기간")
    private int workingDays;

    @Comment("컷수")
    private int cuts;

    @Comment("수정 컷수")
    private int modificationCount;

    @Comment("업로드 기간")
    private PostDurationMonthType postDurationMonthType;
}

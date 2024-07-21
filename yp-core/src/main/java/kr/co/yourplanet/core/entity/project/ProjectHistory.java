package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.util.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@DynamicUpdate
@Builder
@Getter
@IdClass(ProjectHistoryKey.class)
public class ProjectHistory extends BasicColumn {

    @Id
    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Id
    private Integer seq;

    @Column(name = "title")
    private String title;

    @Column(name = "context")
    private String context;

    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @Column(name = "to_date")
    private LocalDateTime toDate;

    @Column(name = "payment")
    private Long payment;

    @Column(name = "cut_number")
    private Integer cutNumber;

    @ManyToOne
    @JoinColumn(name = "request_member_id", referencedColumnName = "id")
    private Member requestMember;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    private List<String> categoryList = new ArrayList<>();

    public ProjectHistory() {
        this.categoryList = new ArrayList<>();
    }
}

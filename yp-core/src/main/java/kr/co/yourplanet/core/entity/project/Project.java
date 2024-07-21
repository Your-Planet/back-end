package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.ValidEnum;
import kr.co.yourplanet.core.util.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@DynamicUpdate
@Getter
@Builder
public class Project extends BasicColumn {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sponsor_id", referencedColumnName = "id")
    private Member sponsor;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Member author;

    @NotBlank
    @Column(name = "title")
    private String title;

    // 비고
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

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    @ValidEnum(enumClass = ProjectStatus.class)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    private List<String> categoryList = new ArrayList<>();

    public Project() {
        this.categoryList = new ArrayList<>();
    }

    public void changeProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void acceptProject(ProjectHistory projectHistory) {
        this.context = projectHistory.getContext();
        this.fromDate = projectHistory.getFromDate();
        this.toDate = projectHistory.getToDate();
        this.payment = projectHistory.getPayment();
        this.cutNumber = projectHistory.getCutNumber();
        this.categoryList = projectHistory.getCategoryList();
        this.projectStatus = ProjectStatus.ACCEPT;
    }
}

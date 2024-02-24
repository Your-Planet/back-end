package kr.co.yourplanet.ypbackend.business.task.domain;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.common.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@Getter
@IdClass(TaskHistoryKey.class)
public class TaskHistory {

    @Id
    @ManyToOne
    @JoinColumn(name = "task_no")
    private Task task;

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
}

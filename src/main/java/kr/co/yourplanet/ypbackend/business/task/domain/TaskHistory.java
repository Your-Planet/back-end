package kr.co.yourplanet.ypbackend.business.task.domain;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Category;
import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "request_context")
    private String requestContext;

    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @Column(name = "toDate")
    private LocalDateTime toDate;

    @Column(name = "payment")
    private Long payment;

    @Column(name = "cut_number")
    private Integer cutNumber;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "request_member_id", referencedColumnName = "id")
    private Member requestMember;
}

package kr.co.yourplanet.ypbackend.business.task.domain;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.common.enums.TaskStatus;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Task {

    @Id
    @GeneratedValue
    private Long taskNo;

    @ManyToOne
    @JoinColumn(name = "advertiser_id", referencedColumnName = "id")
    private Member advertiser;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Member author;

    @Column(name = "request_context")
    private String requestContext;

    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @Column(name = "toDate")
    private LocalDateTime toDate;

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    @ValidEnum(enumClass = TaskStatus.class)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Column(name = "payment")
    private Long payment;
}

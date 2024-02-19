package kr.co.yourplanet.ypbackend.business.task.domain;

import kr.co.yourplanet.ypbackend.business.portfolio.domain.Category;
import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.common.enums.TaskStatus;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
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
@Getter
@Builder
public class Task {

    @Id
    @GeneratedValue
    @Column(name = "task_no")
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

    @Column(name = "payment")
    private Long payment;

    @Column(name = "cut_number")
    private Integer cutNumber;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    @ValidEnum(enumClass = TaskStatus.class)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    public void changeTaskStatus(TaskStatus taskStatus){
        this.taskStatus = taskStatus;
    }

    public void acceptTask(TaskHistory taskHistory){
        this.requestContext = taskHistory.getRequestContext();
        this.fromDate = taskHistory.getFromDate();
        this.toDate = taskHistory.getToDate();
        this.payment = taskHistory.getPayment();
        this.cutNumber = taskHistory.getCutNumber();
        this.category = taskHistory.getCategory();
        this.taskStatus = TaskStatus.ACCEPT;
    }
}

package kr.co.yourplanet.ypbackend.business.task.domain;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.common.StringListConverter;
import kr.co.yourplanet.ypbackend.common.enums.TaskStatus;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
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
@Getter
@Builder
public class Task {

    @Id
    @GeneratedValue
    @Column(name = "task_no")
    private Long taskNo;

    @ManyToOne
    @JoinColumn(name = "sponsor_id", referencedColumnName = "id")
    private Member sponsor;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Member author;

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

    @ValidEnum(enumClass = TaskStatus.class)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Builder.Default
    @Convert(converter = StringListConverter.class)
    private List<String> categoryList = new ArrayList<>();

    public void changeTaskStatus(TaskStatus taskStatus){
        this.taskStatus = taskStatus;
    }

    public void acceptTask(TaskHistory taskHistory){
        this.context = taskHistory.getContext();
        this.fromDate = taskHistory.getFromDate();
        this.toDate = taskHistory.getToDate();
        this.payment = taskHistory.getPayment();
        this.cutNumber = taskHistory.getCutNumber();
        this.categoryList = taskHistory.getCategoryList();
        this.taskStatus = TaskStatus.ACCEPT;
    }
}

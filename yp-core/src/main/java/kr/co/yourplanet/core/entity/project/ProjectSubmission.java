package kr.co.yourplanet.core.entity.project;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "seq"}))
public class ProjectSubmission extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_submission_seq")
    @SequenceGenerator(name = "project_submission_seq", sequenceName = "project_submission_seq", allocationSize = 10)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status", nullable = false)
    private SubmissionStatus submissionStatus;

    @Column(name = "sent_date_time")
    private LocalDateTime sentDateTime;

    @Column(name = "review_date_time")
    private LocalDateTime reviewDateTime;

    @Column(name = "review_message", length = 1000)
    private String reviewMessage;

    public void reviewSubmission(SubmissionStatus status, String reviewMessage) {
        this.submissionStatus = status;
        this.reviewDateTime = LocalDateTime.now();
        this.reviewMessage = reviewMessage;

    }

}

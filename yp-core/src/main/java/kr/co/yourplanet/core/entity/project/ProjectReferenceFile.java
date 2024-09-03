package kr.co.yourplanet.core.entity.project;

import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@Getter
@IdClass(ProjectReferenceFileKey.class)
public class ProjectReferenceFile extends BasicColumn {

    @Id
    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Id
    private Integer seq;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "random_file_name")
    private String randomFileName;

    @Column(name = "reference_file_path")
    private String referenceFilePath;

    @Column(name = "reference_file_url")
    private String referenceFileUrl;

}

package kr.co.yourplanet.core.entity.project;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectHistoryKey implements Serializable {

    private Long project;
    private Integer seq;

    public ProjectHistoryKey(Long projectId, Integer seq) {
        this.project = projectId;
        this.seq = seq;
    }
}

package kr.co.yourplanet.core.entity.task;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskHistoryKey implements Serializable {

    private Task task;
    private Integer seq;
}

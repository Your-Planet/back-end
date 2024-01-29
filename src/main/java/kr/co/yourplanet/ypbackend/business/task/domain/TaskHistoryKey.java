package kr.co.yourplanet.ypbackend.business.task.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskHistoryKey implements Serializable {

    private Task task;
    private Integer seq;
}

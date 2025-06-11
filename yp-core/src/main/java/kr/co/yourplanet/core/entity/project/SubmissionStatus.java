package kr.co.yourplanet.core.entity.project;

import lombok.Getter;

@Getter
public enum SubmissionStatus {

    SUBMITTED("작업물 제출됨"),
    REJECTED("작업물 반려됨"),    // 수정 요청 포함 의미
    CONFIRMED("작업물 확정됨");

    private final String name;

    SubmissionStatus(String name) {
        this.name = name;
    }
}
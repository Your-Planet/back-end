package kr.co.yourplanet.stub;

public class ProjectStub {

    public static long getInProgressProjectWithoutContractId() {
        return 1L;
    }

    public static long getInReviewProjectId() {
        return 2L;
    }

    public static long getInProgressProjectWithContractId() {
        return 3L;
    }

    public static long getInProgressProjectWithCompletedContractId() {
        return 4L;
    }
}

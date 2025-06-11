package kr.co.yourplanet.support.stub;

public class ProjectStub {

    public static long getAcceptedProjectWithoutContractId() {
        return 1L;
    }

    public static long getInReviewProjectId() {
        return 2L;
    }

    public static long getAcceptedProjectWithContractId() {
        return 3L;
    }

    public static long getInProgressProjectWithCompletedContractId() {
        return 4L;
    }
}

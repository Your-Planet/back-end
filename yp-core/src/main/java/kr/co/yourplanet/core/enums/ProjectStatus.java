package kr.co.yourplanet.core.enums;

import java.util.Set;

import lombok.Getter;

@Getter
public enum ProjectStatus {

	DEFAULT("기본", 0, Set.of()),
	IN_REVIEW("의뢰", 72, Set.of(MemberType.SPONSOR)),
	NEGOTIATION_FROM_SPONSOR("광고주협상", 72, Set.of(MemberType.SPONSOR)),
	NEGOTIATION_FROM_CREATOR("작가협상", 72, Set.of(MemberType.CREATOR)),
	CANCELED("취소", 72, Set.of(MemberType.SPONSOR)),
	REJECTED("거절", 72, Set.of(MemberType.CREATOR)),
	ACCEPTED("수락", 72, Set.of(MemberType.CREATOR)),
	IN_PROGRESS("작업중(계약서작성완료)", 0, Set.of()),
	SUBMISSION_SENT("작업물발송", 72, Set.of(MemberType.CREATOR)),
	REQUEST_MODIFICATION("수정요청", 72, Set.of(MemberType.SPONSOR)),
	COMPLETED("완료", 72, Set.of(MemberType.SPONSOR)),
	SETTLED("정산완료", 0, Set.of(MemberType.SPONSOR)),
	CLOSED("마감", 72, Set.of());

	private final String statusName;
	private final int expirationHours;
	private final Set<MemberType> permittedMemberTypes;
	private Set<ProjectStatus> allowedPreviousStatuses;

	ProjectStatus(String statusName, int expirationHours, Set<MemberType> permittedMemberTypeSet) {
		this.statusName = statusName;
		this.expirationHours = expirationHours;
		this.permittedMemberTypes = permittedMemberTypeSet;
	}

	// ProjectStatus enum의 필드 중 Set<ProjectStatus> projectStatusSet은 자기 자신 enum을 참조
	// 따라서 선언된 enum순서에 따라 선언되지 않은 값을 참조하는 오류가 발생할 수 있어, 지연 초기화 처리
	static {
		DEFAULT.allowedPreviousStatuses = Set.of();
		IN_REVIEW.allowedPreviousStatuses = Set.of();
		NEGOTIATION_FROM_CREATOR.allowedPreviousStatuses = Set.of(IN_REVIEW, NEGOTIATION_FROM_SPONSOR);
		NEGOTIATION_FROM_SPONSOR.allowedPreviousStatuses = Set.of(NEGOTIATION_FROM_CREATOR);
		CANCELED.allowedPreviousStatuses = Set.of(IN_REVIEW, NEGOTIATION_FROM_SPONSOR, NEGOTIATION_FROM_CREATOR);
		REJECTED.allowedPreviousStatuses = Set.of(IN_REVIEW, NEGOTIATION_FROM_SPONSOR);
		ACCEPTED.allowedPreviousStatuses = Set.of(IN_REVIEW, NEGOTIATION_FROM_SPONSOR);
		IN_PROGRESS.allowedPreviousStatuses = Set.of(ACCEPTED);
		SUBMISSION_SENT.allowedPreviousStatuses = Set.of(IN_PROGRESS, REQUEST_MODIFICATION);
		REQUEST_MODIFICATION.allowedPreviousStatuses = Set.of(SUBMISSION_SENT);
		COMPLETED.allowedPreviousStatuses = Set.of(SUBMISSION_SENT);
		SETTLED.allowedPreviousStatuses = Set.of(COMPLETED);
		CLOSED.allowedPreviousStatuses = Set.of();
	}

	// ProjectStatus 변경 가능 여부 체크
	public boolean isTransitionAllowed(MemberType requestMemberType, ProjectStatus currentStatus) {

		// permittedMemberTypes이 비어 있으면 requestMemberTypes 검증을 건너뛴다.
		boolean isMemberTypeValid =
			this.permittedMemberTypes.isEmpty() || this.permittedMemberTypes.contains(requestMemberType);

		// allowedPreviousStatuses가 비어 있으면 currentStatus 검증을 건너뛴다.
		boolean isPreviousStatusValid =
			this.allowedPreviousStatuses.isEmpty() || this.allowedPreviousStatuses.contains(currentStatus);

		return isMemberTypeValid && isPreviousStatusValid;
	}
}
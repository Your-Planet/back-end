package kr.co.yourplanet.core.alimtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.alimtalk.AlimTalkRequestHistory;

public interface AlimTalkRequestRepository extends JpaRepository<AlimTalkRequestHistory, Long> {
}

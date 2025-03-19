package kr.co.yourplanet.core.repository;

import kr.co.yourplanet.core.entity.alimtalk.AlimTalkRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimTalkRequestRepository extends JpaRepository<AlimTalkRequestHistory, Long> {
}

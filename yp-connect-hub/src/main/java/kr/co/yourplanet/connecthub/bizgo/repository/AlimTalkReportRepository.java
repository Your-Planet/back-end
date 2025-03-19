package kr.co.yourplanet.connecthub.bizgo.repository;

import kr.co.yourplanet.core.entity.alimtalk.AlimTalkReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimTalkReportRepository extends JpaRepository<AlimTalkReportHistory, Long> {
}

package kr.co.yourplanet.online.business.settlement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.settlement.ProjectSettlement;

public interface ProjectSettlementJpaRepository extends JpaRepository<ProjectSettlement, Long> {

    Optional<ProjectSettlement> findByProjectId(long projectId);
}

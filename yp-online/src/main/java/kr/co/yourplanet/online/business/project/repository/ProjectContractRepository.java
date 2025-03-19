package kr.co.yourplanet.online.business.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.project.ProjectContract;

public interface ProjectContractRepository extends JpaRepository<ProjectContract, Long> {

    Optional<ProjectContract> findByProjectId(Long projectId);
}

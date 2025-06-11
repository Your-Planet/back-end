package kr.co.yourplanet.online.business.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.project.ProjectSubmission;

public interface ProjectSubmissionRepository extends JpaRepository<ProjectSubmission, Long> {
}

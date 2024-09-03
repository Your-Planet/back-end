package kr.co.yourplanet.online.business.project.repository;

import kr.co.yourplanet.core.entity.project.ProjectReferenceFile;
import kr.co.yourplanet.core.entity.project.ProjectReferenceFileKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectReferenceFileRepository extends JpaRepository<ProjectReferenceFile, ProjectReferenceFileKey> {
}

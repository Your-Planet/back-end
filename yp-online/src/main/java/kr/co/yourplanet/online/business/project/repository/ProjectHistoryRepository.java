package kr.co.yourplanet.online.business.project.repository;

import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.entity.project.ProjectHistoryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistory, ProjectHistoryKey> {

    List<ProjectHistory> findAllByProject(Project p);

}

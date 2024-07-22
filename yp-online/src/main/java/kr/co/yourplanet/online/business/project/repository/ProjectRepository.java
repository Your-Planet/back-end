package kr.co.yourplanet.online.business.project.repository;

import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    // Project 영역
    public void saveProject(Project project) {
        em.persist(project);
    }

    public Project findById(Long id) {
        return em.find(Project.class, id);
    }

    // ProjectHistory 영역
    public void saveProjectHistory(ProjectHistory projectHistory) {
        em.persist(projectHistory);
    }

    public List<ProjectHistory> findAllProjectHistoryListById(Project p) {
        return em.createQuery("select ph from ProjectHistory ph where ph.project = :p order by ph.seq asc", ProjectHistory.class)
                .setParameter("p", p)
                .getResultList();
    }
}

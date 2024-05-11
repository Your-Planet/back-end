package kr.co.yourplanet.online.business.task.repository;

import kr.co.yourplanet.core.entity.task.Task;
import kr.co.yourplanet.core.entity.task.TaskHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final EntityManager em;

    // Task 영역
    public void saveTask(Task task) {
        em.persist(task);
    }

    public Task findTaskByTaskNo(Long taskNo) {
        return em.find(Task.class, taskNo);
    }

    // TaskHistory 영역
    public void saveTaskHistory(TaskHistory taskHistory) {
        em.persist(taskHistory);
    }

    public List<TaskHistory> findAllTaskHistoryListByTaskNo(Task t) {
        return em.createQuery("select th from TaskHistory th where th.task = :t order by th.seq asc", TaskHistory.class)
                .setParameter("t", t)
                .getResultList();
    }
}

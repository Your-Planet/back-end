package kr.co.yourplanet.ypbackend.business.task.repository;

import kr.co.yourplanet.ypbackend.business.task.domain.Task;
import kr.co.yourplanet.ypbackend.business.task.domain.TaskHistory;
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

    public List<TaskHistory> findTaskHistoryListByTaskNo(Long taskNo) {
        return em.createQuery("select th from task_history th where th.task_no = :taskNo order by th.seq asc", TaskHistory.class)
                .setParameter("taskNo", taskNo)
                .getResultList();
    }
}

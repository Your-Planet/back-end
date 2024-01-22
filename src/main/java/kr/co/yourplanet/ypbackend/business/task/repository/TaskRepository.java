package kr.co.yourplanet.ypbackend.business.task.repository;

import kr.co.yourplanet.ypbackend.business.task.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final EntityManager em;

    public void saveTask(Task task) {
        em.persist(task);
    }
}

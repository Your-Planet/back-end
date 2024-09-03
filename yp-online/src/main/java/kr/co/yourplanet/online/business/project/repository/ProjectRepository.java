package kr.co.yourplanet.online.business.project.repository;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllBySponsor(Member member);

    List<Project> findAllByCreator(Member member);
}

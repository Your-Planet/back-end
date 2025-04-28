package kr.co.yourplanet.core.alimtalk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;

public interface AlimTalkTemplateRepository extends JpaRepository<AlimTalkTemplate, Long> {

    Optional<AlimTalkTemplate> findByTemplateCode(String templateCode);
}

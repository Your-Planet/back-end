package kr.co.yourplanet.batch.repository;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstagramMediaRepository extends JpaRepository<InstagramMedia, String> {
}

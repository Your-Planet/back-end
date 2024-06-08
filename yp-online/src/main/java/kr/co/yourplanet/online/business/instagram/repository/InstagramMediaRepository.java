package kr.co.yourplanet.online.business.instagram.repository;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstagramMediaRepository extends JpaRepository<InstagramMedia, String> {

    @Query("select im from InstagramMedia im where im.memberId = :memberId and im.permalink like concat('%/p/', :permalink, '/')")
    List<InstagramMedia> findByMemberIdAndPermalinkLike(@Param("memberId") Long memberId, @Param("permalink") String permalink);

    List<InstagramMedia> findByMemberId(Long memberId);
}

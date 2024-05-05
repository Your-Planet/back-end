package kr.co.yourplanet.batch.domain;

import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "member_instagram_info")
public class MemberInstagramInfo extends BasicColumn {

    @Id
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "instagram_id")
    private String instagramId;
    @Column(name = "instagram_user_name")
    private String instagramUserName;
    @Column(name = "instagram_access_token")
    private String instagramAccessToken;
}

package kr.co.yourplanet.core.entity.instagram;

import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "instagram_media")
public class InstagramMedia extends BasicColumn {

    @Id
    private String id;
    @JoinColumn(name = "member_id")
    private Long memberId;
    @Column(name = "caption", length = 1000)
    private String caption;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "media_url", length = 1000)
    private String mediaUrl;
    @Column(name = "permalink", length = 500)
    private String permalink;
    @Column(name = "username")
    private String username;
}

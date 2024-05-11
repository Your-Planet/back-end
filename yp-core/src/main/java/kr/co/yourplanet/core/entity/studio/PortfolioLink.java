package kr.co.yourplanet.core.entity.studio;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@IdClass(PortfolioLinkKey.class)
@Builder
@Getter
public class PortfolioLink extends BasicColumn {

    @Id
    @ManyToOne
    @JoinColumn(name = "studio_id", referencedColumnName = "id")
    private Studio studio;

    @Id
    @OneToOne
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private InstagramMedia media;
}

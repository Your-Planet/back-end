package kr.co.yourplanet.core.entity.portfolio;

import kr.co.yourplanet.core.entity.BasicColumn;
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
    @GeneratedValue
    @Column(name = "seq")
    private Long seq;

    @Column(name = "url")
    private String url;
}

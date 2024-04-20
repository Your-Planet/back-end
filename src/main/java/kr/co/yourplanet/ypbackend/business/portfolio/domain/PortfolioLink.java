package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import kr.co.yourplanet.ypbackend.common.domain.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
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
    @Column(name = "seq")
    private Long seq;

    @Column(name = "url")
    private String url;
}

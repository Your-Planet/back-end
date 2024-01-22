package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@DynamicUpdate
@IdClass(PortfolioLinkKey.class)
@Builder
public class PortfolioLink {

    @Id
    @ManyToOne
    @JoinColumn(name = "portfolio_no")
    private Portfolio portfolio;

    @Id
    @Column(name = "seq")
    private Long seq;

    @Column(name = "url")
    private String url;

}

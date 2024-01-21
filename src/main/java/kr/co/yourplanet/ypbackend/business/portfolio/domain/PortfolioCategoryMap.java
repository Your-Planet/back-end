package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@IdClass(PortfolioCategoryMapKey.class)
public class PortfolioCategoryMap {

    @Id
    @ManyToOne
    @JoinColumn(name = "portfolio_no")
    private Portfolio portfolio;

    @Id
    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;
}

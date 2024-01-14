package kr.co.yourplanet.ypbackend.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioCategoryMap {

    @Column(name = "portfolio_no")
    private Portfolio portfolio;

    @Column(name = "category_code")
    private Category category;
}

package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PortfolioCategoryMapKey implements Serializable {

    private Long portfolio;
    private String category;
}

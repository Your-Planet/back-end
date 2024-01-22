package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PortfolioLinkKey implements Serializable {

    private Long portfolio;
    private Long seq;
}

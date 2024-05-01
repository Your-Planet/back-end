package kr.co.yourplanet.core.entity.portfolio;

import lombok.Data;

import java.io.Serializable;

@Data
public class PortfolioLinkKey implements Serializable {

    private Long studio;
    private Long seq;
}

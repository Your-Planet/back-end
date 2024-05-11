package kr.co.yourplanet.core.entity.studio;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import lombok.Data;

import java.io.Serializable;

@Data
public class PortfolioLinkKey implements Serializable {

    private Long studio;
    private String media;
}

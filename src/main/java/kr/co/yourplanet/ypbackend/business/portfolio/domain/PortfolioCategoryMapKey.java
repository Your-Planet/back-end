package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCategoryMapKey implements Serializable {
    private Long studio;
    private String category;
}

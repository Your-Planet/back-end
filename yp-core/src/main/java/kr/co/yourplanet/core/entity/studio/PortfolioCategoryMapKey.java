package kr.co.yourplanet.core.entity.studio;

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

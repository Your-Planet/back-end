package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import kr.co.yourplanet.ypbackend.business.user.domain.Member;
import kr.co.yourplanet.ypbackend.common.domain.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
public class Studio extends BasicColumn {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(name = "toon_name")
    private String toonName;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<PortfolioCategoryMap> portfolioCategoryMapList;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<PortfolioLink> portfolioLinkList;

    public List<String> getCategoryTypes() {
        return portfolioCategoryMapList.stream()
                .map(PortfolioCategoryMap::getCategory)
                .map(Category::getCategoryName).collect(Collectors.toList());
    }

    public List<String> getPortfolioLinkUrls() {
        return portfolioLinkList.stream()
                .map(PortfolioLink::getUrl).collect(Collectors.toList());
    }

    public void updateStudio(String name, String description) {
        this.toonName = name;
        this.description = description;
    }
}

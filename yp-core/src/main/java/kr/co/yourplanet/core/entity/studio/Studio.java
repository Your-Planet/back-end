package kr.co.yourplanet.core.entity.studio;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member;

    @Column(name = "description")
    private String description;

    @Column(name = "toon_name")
    private String toonName;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<PortfolioCategoryMap> portfolioCategoryMapList;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<PortfolioLink> portfolioLinkList;

    public List<String> getCategoryTypes() {
        return portfolioCategoryMapList.stream()
                .map(PortfolioCategoryMap::getCategory)
                .map(Category::getCategoryCode).collect(Collectors.toList());
    }

    public List<InstagramMedia> getPortfolioLinkUrls() {
        return portfolioLinkList.stream()
                .map(PortfolioLink::getMedia)
                .collect(Collectors.toList());
    }

    public void updateStudioNameAndDescription(String name, String description) {
        this.toonName = name;
        this.description = description;
    }
}

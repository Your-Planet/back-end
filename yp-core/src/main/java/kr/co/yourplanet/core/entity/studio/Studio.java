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
import java.util.Optional;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", unique = true)
    private Member member;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    @OrderBy("createDate ASC")
    private List<Price> priceList;


    @Column(name = "description")
    private String description;

    @Column(name = "toon_name")
    private String toonName;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<StudioCategoryMap> studioCategoryMapList;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY)
    private List<PortfolioLink> portfolioLinkList;

    public List<String> getCategoryTypes() {
        return studioCategoryMapList.stream()
                .map(StudioCategoryMap::getCategory)
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

    public void updateProfileImage(String profileImagePath, String profileImageUrl) {
        this.profileImagePath = profileImagePath;
        this.profileImageUrl = profileImageUrl;
    }

    public Optional<Price> getLatestPrice() {
        return (priceList == null || priceList.isEmpty() || !priceList.get(priceList.size() - 1).isLatest())
                ? Optional.empty()
                : Optional.of(priceList.get(priceList.size() - 1));
    }
}

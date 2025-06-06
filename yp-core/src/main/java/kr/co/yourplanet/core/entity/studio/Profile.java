package kr.co.yourplanet.core.entity.studio;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
public class Profile extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 10)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", unique = true)
    private Member member;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    @OrderBy("createDate ASC")
    private List<Price> priceList;


    @Column(name = "description")
    private String description;

    @Column(name = "toon_name")
    private String toonName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_file_id")
    private FileMetadata profileImageFile;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<ProfileCategoryMap> profileCategoryMapList;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<PortfolioLink> portfolioLinkList;

    public List<String> getCategoryTypes() {
        return profileCategoryMapList.stream()
                .map(ProfileCategoryMap::getCategory)
                .map(Category::getCategoryCode).collect(Collectors.toList());
    }

    public List<InstagramMedia> getPortfolioLinkUrls() {
        return portfolioLinkList.stream()
                .map(PortfolioLink::getMedia)
                .collect(Collectors.toList());
    }

    public void updateNameAndDescription(String name, String description) {
        this.toonName = name;
        this.description = description;
    }

    public void updateProfileImage(FileMetadata file) {
        this.profileImageFile = file;
    }

    public Optional<Price> getLatestPrice() {
        return (priceList == null || priceList.isEmpty() || !priceList.get(priceList.size() - 1).isLatest())
                ? Optional.empty()
                : Optional.of(priceList.get(priceList.size() - 1));
    }

    public boolean hasProfileImage() {
        return this.profileImageFile != null;
    }
}

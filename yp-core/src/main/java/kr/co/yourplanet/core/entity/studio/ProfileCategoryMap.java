package kr.co.yourplanet.core.entity.studio;

import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class ProfileCategoryMap extends BasicColumn {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "category_code", referencedColumnName = "category_code")
    private Category category;
}

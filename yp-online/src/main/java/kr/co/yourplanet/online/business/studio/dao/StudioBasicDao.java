package kr.co.yourplanet.online.business.studio.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class StudioBasicDao {

    private Long id;
    private String toonName;
    private String description;
    private String instagramUsername;
    private String categoryName;
}

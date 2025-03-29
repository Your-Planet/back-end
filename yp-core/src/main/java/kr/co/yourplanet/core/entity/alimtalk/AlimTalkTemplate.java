package kr.co.yourplanet.core.entity.alimtalk;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.enums.AlimTalkCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkTemplate extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alim_talk_template_seq")
    @SequenceGenerator(name = "alim_talk_template_seq", sequenceName = "alim_talk_template_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "template_code", unique = true, nullable = false)
    private String templateCode;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private AlimTalkCategory category;

    @OneToMany(mappedBy = "alimTalkTemplate", fetch = FetchType.LAZY)
    private List<AlimTalkTemplateButton> button;
}

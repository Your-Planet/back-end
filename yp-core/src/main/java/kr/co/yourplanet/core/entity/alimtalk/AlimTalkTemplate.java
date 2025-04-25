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
import kr.co.yourplanet.core.enums.AlimTalkMsgType;
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

    @Column(name = "template_name", nullable = false)
    private String templateName;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "msg_type", nullable = false)
    private AlimTalkMsgType msgType;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private AlimTalkCategory category;

    @OneToMany(mappedBy = "alimTalkTemplate", fetch = FetchType.EAGER)
    private List<AlimTalkTemplateButton> button;
}

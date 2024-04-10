package kr.co.yourplanet.ypbackend.business.user.domain;

import kr.co.yourplanet.ypbackend.common.domain.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSalt extends BasicColumn {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @NotBlank
    private String salt;

    public void updateSalt(String salt){
        this.salt = salt;
    }
}

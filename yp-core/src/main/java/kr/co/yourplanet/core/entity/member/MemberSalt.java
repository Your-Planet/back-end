package kr.co.yourplanet.core.entity.member;

import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSalt extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_salt_seq")
    @SequenceGenerator(name = "member_salt_seq", sequenceName = "member_salt_seq", allocationSize = 10)
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

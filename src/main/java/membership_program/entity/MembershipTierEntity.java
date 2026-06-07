package membership_program.entity;

import jakarta.persistence.*;
import lombok.*;
import membership_program.enums.TierLevel;

import java.util.List;

@Entity
@Table(name = "membership_tier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipTierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TierLevel tierLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private MembershipPlanEntity plan;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MembershipBenefitEntity> benefits;
}

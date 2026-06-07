package membership_program.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membership_benefit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipBenefitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id")
    private MembershipTierEntity tier;

    private String benefitType;   // e.g. FREE_DELIVERY, DISCOUNT, EARLY_ACCESS

    private String benefitValue;  // e.g. "true", "10%", "true"
}

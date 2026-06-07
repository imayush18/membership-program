package membership_program.entity;

import jakarta.persistence.*;
import lombok.*;
import membership_program.enums.PlanType;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "membership_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlanType planType;

    private Integer durationDays;

    private BigDecimal price;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MembershipTierEntity> tiers;
}

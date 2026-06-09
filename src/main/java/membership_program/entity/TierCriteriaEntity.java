package membership_program.entity;

import jakarta.persistence.*;
import lombok.*;
import membership_program.enums.TierLevel;

import java.math.BigDecimal;

@Entity
@Table(name = "tier_criteria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierCriteriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TierLevel tierLevel;

    private Integer minOrderCount;       // e.g. 5 orders to qualify

    private BigDecimal minOrderValue;    // e.g. 1000.00 total order value in a month

    private String cohort;               // e.g. "PREMIUM_USER", "EMPLOYEE" — null means no cohort restriction
}

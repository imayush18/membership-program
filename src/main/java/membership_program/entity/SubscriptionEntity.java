package membership_program.entity;

import jakarta.persistence.*;
import lombok.*;
import membership_program.enums.SubscriptionStatusEnums;

import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private MembershipPlanEntity plan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tier_id")
    private MembershipTierEntity tier;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate cancelledAt;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatusEnums status;

    @Version
    private Long version; // optimistic locking for concurrency
}

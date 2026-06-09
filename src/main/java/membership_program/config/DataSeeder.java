package membership_program.config;

import lombok.RequiredArgsConstructor;
import membership_program.entity.MembershipBenefitEntity;
import membership_program.entity.MembershipPlanEntity;
import membership_program.entity.MembershipTierEntity;
import membership_program.enums.PlanType;
import membership_program.enums.TierLevel;
import membership_program.entity.TierCriteriaEntity;
import membership_program.repository.MembershipPlanRepository;
import membership_program.repository.TierCriteriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MembershipPlanRepository planRepository;
    private final TierCriteriaRepository tierCriteriaRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (planRepository.count() > 0) return;

        planRepository.saveAll(List.of(
                buildPlan("Monthly Plan", PlanType.MONTHLY, 30, new BigDecimal("99.00")),
                buildPlan("Quarterly Plan", PlanType.QUARTERLY, 90, new BigDecimal("249.00")),
                buildPlan("Yearly Plan", PlanType.YEARLY, 365, new BigDecimal("899.00"))
        ));

        tierCriteriaRepository.saveAll(List.of(
                TierCriteriaEntity.builder().tierLevel(TierLevel.SILVER).minOrderCount(3).build(),
                TierCriteriaEntity.builder().tierLevel(TierLevel.GOLD).minOrderCount(10).minOrderValue(new BigDecimal("5000.00")).build(),
                TierCriteriaEntity.builder().tierLevel(TierLevel.PLATINUM).minOrderCount(20).minOrderValue(new BigDecimal("15000.00")).cohort("PREMIUM_USER").build()
        ));
    }

    private MembershipPlanEntity buildPlan(String name, PlanType type, int days, BigDecimal price) {
        MembershipTierEntity silver = buildTier("Silver", "Basic perks", TierLevel.SILVER,
                List.of(benefit("FREE_DELIVERY", "true")));

        MembershipTierEntity gold = buildTier("Gold", "More discounts + early access", TierLevel.GOLD,
                List.of(benefit("FREE_DELIVERY", "true"), benefit("DISCOUNT", "10%"), benefit("EARLY_ACCESS", "true")));

        MembershipTierEntity platinum = buildTier("Platinum", "All perks + priority support", TierLevel.PLATINUM,
                List.of(benefit("FREE_DELIVERY", "true"), benefit("DISCOUNT", "20%"),
                        benefit("EARLY_ACCESS", "true"), benefit("PRIORITY_SUPPORT", "true")));

        MembershipPlanEntity plan = MembershipPlanEntity.builder()
                .name(name).planType(type).durationDays(days).price(price)
                .tiers(List.of(silver, gold, platinum))
                .build();

        silver.setPlan(plan);
        gold.setPlan(plan);
        platinum.setPlan(plan);

        return plan;
    }

    private MembershipTierEntity buildTier(String name, String desc, TierLevel level,
                                           List<MembershipBenefitEntity> benefits) {
        MembershipTierEntity tier = MembershipTierEntity.builder()
                .name(name).description(desc).tierLevel(level).benefits(benefits).build();
        benefits.forEach(b -> b.setTier(tier));
        return tier;
    }

    private MembershipBenefitEntity benefit(String type, String value) {
        return MembershipBenefitEntity.builder().benefitType(type).benefitValue(value).build();
    }
}

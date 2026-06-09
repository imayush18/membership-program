package membership_program.service;

import lombok.RequiredArgsConstructor;
import membership_program.dto.*;
import membership_program.entity.MembershipPlanEntity;
import membership_program.entity.MembershipTierEntity;
import membership_program.entity.SubscriptionEntity;
import membership_program.enums.SubscriptionStatusEnums;
import membership_program.enums.TierLevel;
import membership_program.exception.ResourceNotFoundException;
import membership_program.repository.MembershipPlanRepository;
import membership_program.repository.MembershipTierRepository;
import membership_program.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final SubscriptionRepository subscriptionRepository;

    public List<PlanResponse> getAllPlans() {
        return planRepository.findAll().stream().map(this::toPlanResponse).toList();
    }

    @Transactional
    public SubscriptionResponse subscribe(SubscribeRequest request) {
        subscriptionRepository.findByUserIdAndStatus(request.getUserId(), SubscriptionStatusEnums.ACTIVE)
                .ifPresent(s -> { throw new IllegalStateException("User already has an active subscription"); });

        MembershipPlanEntity plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found: " + request.getPlanId()));

        MembershipTierEntity tier = tierRepository.findById(request.getTierId())
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found: " + request.getTierId()));

        if (!tier.getPlan().getId().equals(plan.getId())) {
            throw new IllegalStateException("Tier " + tier.getId() + " does not belong to plan " + plan.getId());
        }

        SubscriptionEntity subscription = SubscriptionEntity.builder()
                .userId(request.getUserId())
                .plan(plan)
                .tier(tier)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(plan.getDurationDays()))
                .status(SubscriptionStatusEnums.ACTIVE)
                .build();

        return toSubscriptionResponse(subscriptionRepository.save(subscription));
    }

    @Transactional
    public SubscriptionResponse upgradeTier(Long userId) {
        SubscriptionEntity subscription = getActiveSubscription(userId);
        TierLevel nextLevel = subscription.getTier().getTierLevel().next();
        MembershipTierEntity nextTier = tierRepository
                .findByPlanIdAndTierLevel(subscription.getPlan().getId(), nextLevel)
                .orElseThrow(() -> new ResourceNotFoundException("No tier found for level: " + nextLevel));
        subscription.setTier(nextTier);
        return toSubscriptionResponse(subscriptionRepository.save(subscription));
    }

    @Transactional
    public SubscriptionResponse downgradeTier(Long userId) {
        SubscriptionEntity subscription = getActiveSubscription(userId);
        TierLevel prevLevel = subscription.getTier().getTierLevel().previous();
        MembershipTierEntity prevTier = tierRepository
                .findByPlanIdAndTierLevel(subscription.getPlan().getId(), prevLevel)
                .orElseThrow(() -> new ResourceNotFoundException("No tier found for level: " + prevLevel));
        subscription.setTier(prevTier);
        return toSubscriptionResponse(subscriptionRepository.save(subscription));
    }

    @Transactional
    public SubscriptionResponse cancel(Long userId) {
        SubscriptionEntity subscription = getActiveSubscription(userId);
        subscription.setStatus(SubscriptionStatusEnums.CANCELLED);
        subscription.setCancelledAt(LocalDate.now());
        return toSubscriptionResponse(subscriptionRepository.save(subscription));
    }

    public SubscriptionResponse getSubscription(Long userId) {
        return toSubscriptionResponse(getActiveSubscription(userId));
    }

    private SubscriptionEntity getActiveSubscription(Long userId) {
        SubscriptionEntity subscription = subscriptionRepository
                .findByUserIdAndStatus(userId, SubscriptionStatusEnums.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("No active subscription for user: " + userId));

        if (subscription.getEndDate().isBefore(LocalDate.now())) {
            subscription.setStatus(SubscriptionStatusEnums.EXPIRED);
            subscriptionRepository.save(subscription);
            throw new ResourceNotFoundException("Subscription has expired for user: " + userId);
        }
        return subscription;
    }

    private PlanResponse toPlanResponse(MembershipPlanEntity plan) {
        List<TierResponse> tiers = plan.getTiers() == null ? List.of() :
                plan.getTiers().stream().map(this::toTierResponse).toList();
        return PlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .planType(plan.getPlanType())
                .durationDays(plan.getDurationDays())
                .price(plan.getPrice())
                .tiers(tiers)
                .build();
    }

    private TierResponse toTierResponse(MembershipTierEntity tier) {
        List<BenefitResponse> benefits = tier.getBenefits() == null ? List.of() :
                tier.getBenefits().stream()
                        .map(b -> BenefitResponse.builder()
                                .benefitType(b.getBenefitType())
                                .benefitValue(b.getBenefitValue())
                                .build())
                        .toList();
        return TierResponse.builder()
                .id(tier.getId())
                .name(tier.getName())
                .description(tier.getDescription())
                .tierLevel(tier.getTierLevel())
                .benefits(benefits)
                .build();
    }

    private SubscriptionResponse toSubscriptionResponse(SubscriptionEntity s) {
        return SubscriptionResponse.builder()
                .subscriptionId(s.getId())
                .userId(s.getUserId())
                .planName(s.getPlan().getName())
                .tierName(s.getTier().getName())
                .tierLevel(s.getTier().getTierLevel().name())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .cancelledAt(s.getCancelledAt())
                .status(s.getStatus())
                .build();
    }
}

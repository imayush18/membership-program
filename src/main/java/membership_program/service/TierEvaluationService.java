package membership_program.service;

import lombok.RequiredArgsConstructor;
import membership_program.dto.UserContext;
import membership_program.entity.TierCriteriaEntity;
import membership_program.enums.TierLevel;
import membership_program.repository.TierCriteriaRepository;
import membership_program.strategy.TierEvaluationStrategy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TierEvaluationService {

    private final TierCriteriaRepository tierCriteriaRepository;
    private final List<TierEvaluationStrategy> strategies; // Spring injects all implementations

    /**
     * Evaluates user context against all tier criteria using all strategies.
     * Returns the highest tier the user qualifies for, or SILVER as default.
     */
    public TierLevel evaluate(UserContext context) {
        // evaluate from highest to lowest, return first match
        return Arrays.stream(TierLevel.values())
                .sorted((a, b) -> b.getLevel() - a.getLevel()) // PLATINUM → GOLD → SILVER
                .filter(tierLevel -> isEligibleForTier(context, tierLevel))
                .findFirst()
                .orElse(TierLevel.SILVER); // default to lowest tier
    }

    private boolean isEligibleForTier(UserContext context, TierLevel tierLevel) {
        Optional<TierCriteriaEntity> criteria = tierCriteriaRepository.findByTierLevel(tierLevel);
        if (criteria.isEmpty()) return false;
        // user is eligible if ANY strategy says they qualify
        return strategies.stream().anyMatch(strategy -> strategy.isEligible(context, criteria.get()));
    }
}

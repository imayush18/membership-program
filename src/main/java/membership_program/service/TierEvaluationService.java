package membership_program.service;

import lombok.RequiredArgsConstructor;
import membership_program.dto.UserContext;
import membership_program.enums.TierLevel;
import membership_program.repository.TierCriteriaRepository;
import membership_program.strategy.TierEvaluationStrategy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        return Arrays.stream(TierLevel.values())
                .sorted(Comparator.comparingInt(TierLevel::getLevel).reversed())
                .filter(tierLevel -> isEligibleForTier(context, tierLevel))
                .findFirst()
                .orElse(TierLevel.SILVER);
    }

    private boolean isEligibleForTier(UserContext context, TierLevel tierLevel) {
        return tierCriteriaRepository.findByTierLevel(tierLevel)
                .map(criteria -> strategies.stream().anyMatch(strategy -> strategy.isEligible(context, criteria)))
                .orElse(false);
    }
}

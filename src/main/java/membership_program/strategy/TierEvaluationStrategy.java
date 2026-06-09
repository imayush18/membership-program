package membership_program.strategy;

import membership_program.dto.UserContext;
import membership_program.entity.TierCriteriaEntity;

public interface TierEvaluationStrategy {

    boolean isEligible(UserContext context, TierCriteriaEntity criteria);
}

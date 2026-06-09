package membership_program.strategy;

import membership_program.dto.UserContext;
import membership_program.entity.TierCriteriaEntity;
import org.springframework.stereotype.Component;

@Component
public class CohortStrategy implements TierEvaluationStrategy {

    @Override
    public boolean isEligible(UserContext context, TierCriteriaEntity criteria) {
        if (criteria.getCohort() == null || context.getCohort() == null) return false;
        return criteria.getCohort().equalsIgnoreCase(context.getCohort());
    }
}

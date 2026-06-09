package membership_program.strategy;

import membership_program.dto.UserContext;
import membership_program.entity.TierCriteriaEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderValueStrategy implements TierEvaluationStrategy {

    @Override
    public boolean isEligible(UserContext context, TierCriteriaEntity criteria) {
        if (criteria.getMinOrderValue() == null || context.getTotalOrderValue() == null) return false;
        return context.getTotalOrderValue().compareTo(criteria.getMinOrderValue()) >= 0;
    }
}

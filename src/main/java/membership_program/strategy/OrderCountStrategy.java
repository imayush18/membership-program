package membership_program.strategy;

import membership_program.dto.UserContext;
import membership_program.entity.TierCriteriaEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderCountStrategy implements TierEvaluationStrategy {

    @Override
    public boolean isEligible(UserContext context, TierCriteriaEntity criteria) {
        if (criteria.getMinOrderCount() == null) return false;
        return context.getOrderCount() >= criteria.getMinOrderCount();
    }
}

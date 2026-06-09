package membership_program.dto;

import lombok.Builder;
import lombok.Data;
import membership_program.enums.TierLevel;

@Data
@Builder
public class TierEvaluationResponse {
    private Long userId;
    private TierLevel recommendedTier;
}

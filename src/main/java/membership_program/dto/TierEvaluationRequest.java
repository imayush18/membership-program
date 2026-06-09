package membership_program.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TierEvaluationRequest {
    @NotNull
    private Long userId;
    private int orderCount;
    private BigDecimal totalOrderValue;
    private String cohort;
}

package membership_program.dto;

import lombok.Builder;
import lombok.Data;
import membership_program.enums.PlanType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PlanResponse {
    private Long id;
    private String name;
    private PlanType planType;
    private Integer durationDays;
    private BigDecimal price;
    private List<TierResponse> tiers;
}

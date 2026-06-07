package membership_program.dto;

import lombok.Builder;
import lombok.Data;
import membership_program.enums.TierLevel;

import java.util.List;

@Data
@Builder
public class TierResponse {
    private Long id;
    private String name;
    private String description;
    private TierLevel tierLevel;
    private List<BenefitResponse> benefits;
}

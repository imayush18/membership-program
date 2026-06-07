package membership_program.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BenefitResponse {
    private String benefitType;
    private String benefitValue;
}

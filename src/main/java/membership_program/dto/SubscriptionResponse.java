package membership_program.dto;

import lombok.Builder;
import lombok.Data;
import membership_program.enums.SubscriptionStatus;

import java.time.LocalDate;

@Data
@Builder
public class SubscriptionResponse {
    private Long subscriptionId;
    private Long userId;
    private String planName;
    private String tierName;
    private String tierLevel;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate cancelledAt;
    private SubscriptionStatus status;
}

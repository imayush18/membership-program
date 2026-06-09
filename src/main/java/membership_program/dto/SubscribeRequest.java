package membership_program.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscribeRequest {
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "planId is required")
    private Long planId;
    @NotNull(message = "tierId is required")
    private Long tierId;
}

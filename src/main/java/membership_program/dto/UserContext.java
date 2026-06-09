package membership_program.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserContext {
    private Long userId;
    private int orderCount;           // number of orders placed
    private BigDecimal totalOrderValue; // total order value in current month
    private String cohort;            // e.g. "PREMIUM_USER", "EMPLOYEE", null
}

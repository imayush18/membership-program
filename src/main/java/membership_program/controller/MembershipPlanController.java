package membership_program.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import membership_program.dto.PlanResponse;
import membership_program.dto.TierEvaluationRequest;
import membership_program.dto.TierEvaluationResponse;
import membership_program.dto.UserContext;
import membership_program.service.MembershipService;
import membership_program.service.TierEvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
@Tag(name = "Membership Plans", description = "APIs to view plans and evaluate tier eligibility")
public class MembershipPlanController {

    private final MembershipService membershipService;
    private final TierEvaluationService tierEvaluationService;

    @GetMapping
    @Operation(summary = "Get all membership plans", description = "Returns all plans with their tiers and benefits")
    public ResponseEntity<List<PlanResponse>> getAllPlans() {
        return ResponseEntity.ok(membershipService.getAllPlans());
    }

    @PostMapping("/evaluate-tier")
    @Operation(summary = "Evaluate recommended tier", description = "Returns the highest tier a user qualifies for based on order count, order value and cohort")
    public ResponseEntity<TierEvaluationResponse> evaluateTier(@Valid @RequestBody TierEvaluationRequest request) {
        UserContext context = UserContext.builder()
                .userId(request.getUserId())
                .orderCount(request.getOrderCount())
                .totalOrderValue(request.getTotalOrderValue())
                .cohort(request.getCohort())
                .build();
        return ResponseEntity.ok(TierEvaluationResponse.builder()
                .userId(request.getUserId())
                .recommendedTier(tierEvaluationService.evaluate(context))
                .build());
    }
}

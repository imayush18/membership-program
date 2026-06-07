package membership_program.controller;

import lombok.RequiredArgsConstructor;
import membership_program.dto.PlanResponse;
import membership_program.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class MembershipPlanController {

    private final MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getAllPlans() {
        return ResponseEntity.ok(membershipService.getAllPlans());
    }
}

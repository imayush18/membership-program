package membership_program.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import membership_program.dto.SubscribeRequest;
import membership_program.dto.SubscriptionResponse;
import membership_program.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "APIs to subscribe, upgrade, downgrade and cancel memberships")
public class SubscriptionController {

    private final MembershipService membershipService;

    @PostMapping
    @Operation(summary = "Subscribe to a plan", description = "Subscribe a user to a membership plan and tier")
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscribeRequest request) {
        return ResponseEntity.ok(membershipService.subscribe(request));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get current subscription", description = "Fetch active subscription details and expiry for a user")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.getSubscription(userId));
    }

    @PutMapping("/{userId}/upgrade")
    @Operation(summary = "Upgrade tier", description = "Upgrade user's current tier to the next level")
    public ResponseEntity<SubscriptionResponse> upgrade(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.upgradeTier(userId));
    }

    @PutMapping("/{userId}/downgrade")
    @Operation(summary = "Downgrade tier", description = "Downgrade user's current tier to the previous level")
    public ResponseEntity<SubscriptionResponse> downgrade(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.downgradeTier(userId));
    }

    @PutMapping("/{userId}/cancel")
    @Operation(summary = "Cancel subscription", description = "Cancel the user's active subscription")
    public ResponseEntity<SubscriptionResponse> cancel(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.cancel(userId));
    }
}

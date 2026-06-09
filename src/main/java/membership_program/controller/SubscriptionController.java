package membership_program.controller;

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
public class SubscriptionController {

    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscribeRequest request) {
        return ResponseEntity.ok(membershipService.subscribe(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.getSubscription(userId));
    }

    @PutMapping("/{userId}/upgrade")
    public ResponseEntity<SubscriptionResponse> upgrade(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.upgradeTier(userId));
    }

    @PutMapping("/{userId}/downgrade")
    public ResponseEntity<SubscriptionResponse> downgrade(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.downgradeTier(userId));
    }

    @PutMapping("/{userId}/cancel")
    public ResponseEntity<SubscriptionResponse> cancel(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.cancel(userId));
    }
}

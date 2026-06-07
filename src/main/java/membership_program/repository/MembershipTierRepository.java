package membership_program.repository;

import membership_program.entity.MembershipTierEntity;
import membership_program.enums.TierLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipTierRepository extends JpaRepository<MembershipTierEntity, Long> {

    Optional<MembershipTierEntity> findByPlanIdAndTierLevel(Long planId, TierLevel tierLevel);
}

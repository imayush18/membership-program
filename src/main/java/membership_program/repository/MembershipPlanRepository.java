package membership_program.repository;

import membership_program.entity.MembershipPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlanRepository
        extends JpaRepository<MembershipPlanEntity, Long> {
}
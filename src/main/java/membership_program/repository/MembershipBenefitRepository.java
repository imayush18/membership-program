package membership_program.repository;

import membership_program.entity.MembershipBenefitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipBenefitRepository extends JpaRepository<MembershipBenefitEntity, Long> {
}

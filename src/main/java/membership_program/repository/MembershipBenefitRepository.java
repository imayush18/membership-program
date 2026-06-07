package membership_program.repository;

import membership_program.entity.MembershipBenefitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipBenefitRepository extends JpaRepository<MembershipBenefitEntity, Long> {

    List<MembershipBenefitEntity> findByTierId(Long tierId);
}

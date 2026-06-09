package membership_program.repository;

import membership_program.entity.TierCriteriaEntity;
import membership_program.enums.TierLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TierCriteriaRepository extends JpaRepository<TierCriteriaEntity, Long> {

    Optional<TierCriteriaEntity> findByTierLevel(TierLevel tierLevel);
}

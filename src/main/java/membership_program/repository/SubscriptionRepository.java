package membership_program.repository;

import membership_program.entity.SubscriptionEntity;
import membership_program.enums.SubscriptionStatusEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findByUserIdAndStatus(Long userId, SubscriptionStatusEnums status);
}

package site.balpyo.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.balpyo.ai.entity.AIGenerateLogEntity;

@Repository
public interface AIGenerateLogRepository extends JpaRepository<AIGenerateLogEntity, Long> {
}

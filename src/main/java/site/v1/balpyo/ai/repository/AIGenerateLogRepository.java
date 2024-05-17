package site.v1.balpyo.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.v1.balpyo.ai.entity.AIGenerateLogEntity;
import site.v1.balpyo.ai.entity.GPTInfoEntity;

import java.util.Optional;

@Repository
public interface AIGenerateLogRepository extends JpaRepository<AIGenerateLogEntity, Long> {
    Optional<AIGenerateLogEntity> findByGptInfoEntity(GPTInfoEntity gptInfoEntity);
}

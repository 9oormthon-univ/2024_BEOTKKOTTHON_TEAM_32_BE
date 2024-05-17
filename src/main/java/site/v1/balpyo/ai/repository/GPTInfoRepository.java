package site.v1.balpyo.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.v1.balpyo.ai.entity.GPTInfoEntity;

@Repository
public interface GPTInfoRepository extends JpaRepository<GPTInfoEntity, String> {
}

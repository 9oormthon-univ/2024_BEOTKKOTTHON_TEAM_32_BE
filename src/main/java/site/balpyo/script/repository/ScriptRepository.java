package site.balpyo.script.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.balpyo.script.entity.ScriptEntity;

public interface ScriptRepository extends JpaRepository<ScriptEntity, Long> {
}

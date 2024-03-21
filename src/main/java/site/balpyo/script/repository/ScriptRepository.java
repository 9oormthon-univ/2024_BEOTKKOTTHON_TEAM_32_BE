package site.balpyo.script.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.balpyo.script.entity.ScriptEntity;
import site.balpyo.user.entity.GuestEntity;

public interface ScriptRepository extends JpaRepository<ScriptEntity, Long> {
}

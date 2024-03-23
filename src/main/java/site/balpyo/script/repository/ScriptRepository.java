package site.balpyo.script.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.balpyo.script.entity.ScriptEntity;

import java.util.Optional;

public interface ScriptRepository extends JpaRepository<ScriptEntity, Long> {
    @Query("SELECT s FROM ScriptEntity s WHERE s.guestEntity.uid = :uid AND s.script_id = :scriptId")
    Optional<ScriptEntity> findScriptByGuestUidAndScriptId(@Param("uid") String uid, @Param("scriptId") Long scriptId);

}

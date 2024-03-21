package site.balpyo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.balpyo.ai.entity.AIGenerateLogEntity;
import site.balpyo.user.entity.GuestEntity;

import java.rmi.server.UID;

public interface GuestRepository  extends JpaRepository<GuestEntity, String> {
}

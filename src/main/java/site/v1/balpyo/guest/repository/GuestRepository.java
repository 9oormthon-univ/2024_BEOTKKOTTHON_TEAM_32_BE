package site.v1.balpyo.guest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.v1.balpyo.guest.entity.GuestEntity;

public interface GuestRepository  extends JpaRepository<GuestEntity, String> {
}

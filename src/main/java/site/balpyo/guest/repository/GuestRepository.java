package site.balpyo.guest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.balpyo.guest.entity.GuestEntity;

public interface GuestRepository  extends JpaRepository<GuestEntity, String> {
}

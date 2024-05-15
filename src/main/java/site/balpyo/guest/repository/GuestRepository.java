package site.balpyo.guest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.balpyo.guest.entity.GuestEntity;

import java.util.List;

public interface GuestRepository  extends JpaRepository<GuestEntity, String> {
}

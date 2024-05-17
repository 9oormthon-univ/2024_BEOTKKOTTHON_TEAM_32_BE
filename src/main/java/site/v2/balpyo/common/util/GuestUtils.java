package site.v2.balpyo.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.v2.balpyo.guest.entity.GuestEntity;
import site.v2.balpyo.guest.repository.GuestRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GuestUtils {

    public static boolean verifyUID(String uid, GuestRepository guestRepository){
        Optional<GuestEntity> guestEntity = guestRepository.findById(uid);
        return guestEntity.isPresent();
    }
}

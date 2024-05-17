package site.v1.balpyo.guest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.v1.balpyo.common.dto.CommonResponse;
import site.v1.balpyo.common.util.GuestUtils;
import site.v1.balpyo.guest.dto.UIDResponse;
import site.v1.balpyo.guest.dto.VerifyResponse;
import site.v1.balpyo.guest.entity.GuestEntity;
import site.v1.balpyo.guest.repository.GuestRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    public ResponseEntity<CommonResponse> generateUID(){
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        GuestEntity guestEntity = GuestEntity.builder()
                .uid(uuidString)
                .build();
        guestRepository.save(guestEntity);

        return CommonResponse.success(new UIDResponse(uuidString));
    }

    public ResponseEntity<CommonResponse> verifyUID(String uid) {
        boolean isVerified = GuestUtils.verifyUID(uid,guestRepository);

        return CommonResponse.success(new VerifyResponse(isVerified,uid));
    }
}

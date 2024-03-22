package site.balpyo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    //8000 - GPT 계열에러
    GPT_GENERATION_ERROR("8001", "GPT 스크립트 생성 실패."),
    GPT_API_KEY_MISSING("8002", "GPT API 키 누락."),

    //9000 - client 계열 에러
    BALPYO_API_KEY_ERROR("9000", "BALPYO_API_KEY를 다시 확인해주세요."),
    BALPYO_UID_KEY_MISSING("9001", "헤더의 UID를 다시 확인해주세요."),

    // 5000 - 내부 서버 에러
    INTERNAL_SERVER_ERROR("5000", "내부 서버 오류가 발생했습니다.");


    private final String code;
    private final String message;

}

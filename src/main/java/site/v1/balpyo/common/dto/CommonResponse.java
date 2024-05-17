package site.v1.balpyo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@AllArgsConstructor
public class CommonResponse {

    private String code;
    private String message;
    private Object result;

    /**
     * 성공 응답을 위한 ResponseEntity를 생성
     * "0000" 코드와 "success" 메시지를 사용하여 성공 응답을 나타냄
     *
     * @param result API 호출 결과로 반환할 객체
     * @return 성공 상태(200 OK)와 결과를 포함하는 ResponseEntity 객체
     */
    public static ResponseEntity<CommonResponse> success(Object result) {
        CommonResponse commonResponse = new CommonResponse("0000", "success", result);
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 에러 응답을 위한 ResponseEntity를 생성
     * @param errorEnum 에러 상황을 나타내는 Enum
     * @return 내부 서버 오류 상태(500)와 메세지를 포함하는 ResponseEntity 객체
     */
    public static ResponseEntity<CommonResponse> error(ErrorEnum errorEnum) {
        CommonResponse commonResponse = new CommonResponse(errorEnum.getCode(), errorEnum.getMessage(),"");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonResponse);
    }

}

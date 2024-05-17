package site.v1.balpyo.common.util;

public class CommonUtils {

    /**
     * 제공된 매개변수들 중 하나라도 {@code null}인지 여부를 검사
     *
     * 이 메서드는 가변 인자를 사용하여 여러 객체를 매개변수로 받아,
     * 그 중 어느 하나라도 {@code null} 값이 있는지 검사한 후 결과를 반환
     * 모든 매개변수가 {@code null}이 아닌 경우에만 {@code false}를 반환
     *
     * @param parameters 검사할 매개변수들. 여러 매개변수가 가변 인자로 전달
     * @return 매개변수 중 하나라도 {@code null}이면 {@code true}, 그렇지 않으면 {@code false}.
     */
    public static boolean isAnyParameterNullOrBlank(Object... parameters) {
        for (Object param : parameters) {
            if (param == null) {
                return true;
            }
            if (param instanceof String && ((String) param).isEmpty()) {
                return true;
            }
        }
        return false;
    }

}

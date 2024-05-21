package Backend.socket.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    INVALID_IMAGE_TYPE(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일입니다."),
    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ROADMAP_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 로드맵 타입입니다."),
    INVALID_TEMPLATE_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 회의록 타입입니다."),
    INVALID_USER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 유저 타입니다."),
    INVALID_NOTIFICATION_TOP_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 알림 탑카테고리입니다."),

    /**
     * 404 Not Found
     */
    CHATTING_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅 정보를 찾을 수 없습니다."),
    MESSAGE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "모임 정보를 찾을 수 없습니다."),
    SEARCH_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "검색 종류를 찾을 수 없습니다."),
    SUB_SEARCH_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "세부 검색 종류를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    S3_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}

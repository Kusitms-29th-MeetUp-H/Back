package com.kusitms29.backendH.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_USER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 유저 타입니다."),
    INVALID_PLATFORM_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 플랫폼입니다"),
    INVALID_IMAGE_TYPE(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일입니다."),
    INVALID_SYNC_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 싱크타입입니다."),

    INVALID_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 관심사입니다."),
    INVALID_LANGUAGE_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 언어타입입니다."),
    INVALID_PARENT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리부모타입입니다."),
    INVALID_GENDER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 성별타입입니다."),
    INVALID_NOTIFICATION_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 알림 타입입니다."),
    INVALID_NOTIFICATION_TOP_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 알림 탑카테고리입니다."),
    INVALID_UNIVERSITY_NAME(HttpStatus.BAD_REQUEST, "유효하지 않은 대학이름입니다."),
    INVALID_UNIVERSITY_DOMAIN(HttpStatus.BAD_REQUEST, "대학과 일치하지 않는 메일 도메인입니다."),
    INVALID_POST_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 게시물타입입니다."),
    INVALID_PARENT_CHILD_CATEGORY(HttpStatus.BAD_REQUEST, "부모 카테고리가 불일치합니다."),



    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 형식이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "일치하지 않는 리프레시 토큰입니다."),
    INVALID_AUTH_CODE(HttpStatus.UNAUTHORIZED, "인증을 실패했습니다."),


    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다."),

    /**
     * 404 Not Found
     */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "엔티티를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    SYNC_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 싱크입니다."),
    FCMTOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "FCM 토큰을 찾을 수 없습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),
    PARTICIPATION_NOT_FOUND(HttpStatus.NOT_FOUND, "참여자를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    POST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물좋아요를 찾을 수 없습니다."),
    COMMENT_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글좋아요를 찾을 수 없습니다."),
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "대댓글을 찾을 수 없습니다."),
    REPLY_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "대댓글좋아요를 찾을 수 없습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),
    PARTICIPATION_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "최대 인원 수가 모두 채워진 싱크입니다."),
    TOO_LONG_TITLE_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "제목은 30자까지만 작성할 수 있어요"),
    TOO_LONG_CONTENT_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "내용은 300자까지만 작성할 수 있어요"),
    TOO_LONG_COMMENT_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "댓글은 30자까지만 작성할 수 있어요"),
    TOO_MANY_IMAGES_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "이미지는 최대 5개까지 입니다."),
    SYNC_NAME_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "싱크 제목은 15자까지만 작성할 수 있어요"),
    SYNC_INTRO_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "싱크 내용은 500자까지만 작성할 수 있어요."),
    USER_INTRO_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "싱크장 소개는 50자까지만 작성할 수 있어요,"),
    SYNC_MIN_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "최소인원은 3명부터입니다."),
    SYNC_MAX_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "최대인원은 30명까지입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    DUPLICATE_TEAM(HttpStatus.CONFLICT, "이미 존재하는 팀입니다."),
    DUPLICATE_PARTICIPATION(HttpStatus.CONFLICT, "이미 참여했습니다."),
    DUPLICATE_SCHOOL_MAIL(HttpStatus.CONFLICT, "이미 메일을 보냈습니다."),
    DUPLICATE_POST_LIKE(HttpStatus.CONFLICT, "이미 게시글을 좋아요했습니다."),
    DUPLICATE_COMMENT_LIKE(HttpStatus.CONFLICT, "이미 댓글을 좋아요했습니다."),
    DUPLICATE_REPLY_LIKE(HttpStatus.CONFLICT, "이미 대댓글을 좋아요했습니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    JSON_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Json 으로 변환할 수 없는 String 입니다."),
    S3_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다."),
    MAIL_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "메일 전송에 실패했습니다."),
    UNIVERSITY_API_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "대학 검증 API요청을 실패했습니다."),
    PAPAGO_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파파고 API요청을 실패했습니다."),
    SEOUL_ADDRESS_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서울 주소 요청을 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}

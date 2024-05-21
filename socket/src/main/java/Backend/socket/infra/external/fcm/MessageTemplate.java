package Backend.socket.infra.external.fcm;

import lombok.Getter;

@Getter
public enum MessageTemplate {
    COMMENT("커뮤니티","\"%s\"글에 \"%s\"님이 댓글을 달았어요."),
    CHAT("채팅","\"%s\"의 새로운 메세지가 도착했어요."),
    CHAT_ROOM_NOTICE("공지", "\"%s\" 싱크의 새로운 채팅방이 생겼어요."),
    SYNC_REMINDER("일정", "%s님! 오늘은 \"%s\" 싱크하는 날이에요."),
    REVIEW("후기","%s님! 즐거운 싱크 되셨나요?");

    private final String title;
    private final String content;

    MessageTemplate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

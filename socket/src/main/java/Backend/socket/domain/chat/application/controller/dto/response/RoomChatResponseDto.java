package Backend.socket.domain.chat.application.controller.dto.response;

import Backend.socket.domain.chat.domain.Room;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class RoomChatResponseDto {
    private String syncName;
    private int total;
    private String content;
    private String time;


    public static RoomChatResponseDto of(Room room, String content, LocalDateTime time){
        return RoomChatResponseDto.builder()
                .syncName(room.getSyncName())
                .total(room.getChatUserList().size())
                .content(content)
                .time(calculateTimeDifference(time))
                .build();


    }
    public static String calculateTimeDifference(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(time, now);
        long months = duration.toHours() / 60;
        if (months > 0) {
            return months + "분 전";
        }
        return "방금 전";
    }
}

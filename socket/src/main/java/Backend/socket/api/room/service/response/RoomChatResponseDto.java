package Backend.socket.api.room.service.response;

import Backend.socket.domain.chat.domain.Room;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@Getter
public class RoomChatResponseDto {
    private String syncName;
    private String image;
    private String roomName;
    private int total;
    private String content;
    private String time;
    private String ownerSessionId;


    public static RoomChatResponseDto of(Room room, String content, LocalDateTime time){
        return RoomChatResponseDto.builder()
                .syncName(room.getSyncName())
                .image(room.getImage())
                .roomName(room.getRoomName())
                .total(room.getChatUserList().size())
                .content(content)
                .time(calculateTimeDifference(time))
                .ownerSessionId(room.getOwnerSession())
                .build();


    }
    public static String calculateTimeDifference(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(date, now);

        long minutes = duration.toMinutes();
        if (minutes < 1) {
            return "방금 전";
        }

        if (minutes < 60) {
            return minutes + "분 전";
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return hours + "시간 전";
        }

        long days = duration.toDays();
        if (days < 7) {
            return days + "일 전";
        }

        long weeks = days / 7;
        if (weeks < 4) {
            return weeks + "주 전";
        }

        long months = days / 30;
        if (months < 12) {
            return months + "달 전";
        }

        long years = months / 12;
        return years + "년 전";
    }
}

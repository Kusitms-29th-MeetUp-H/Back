package Backend.socket.domain.chat.application.controller.dto.response;

import Backend.socket.domain.chat.application.service.TriFunction;
import Backend.socket.domain.chat.domain.ChatContent;
import Backend.socket.domain.chat.domain.ChatUser;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class RoomMessageElementResponseDto {
    private ChatUserResponseDto user;
    private String content;
    private String time;

    public static List<RoomMessageElementResponseDto> listOf(List<ChatContent> chatContentList,String roomName,TriFunction<String, String, ChatUser> formatter) {
        return chatContentList.stream()
                .map(chatContent -> RoomMessageElementResponseDto.of(chatContent,roomName,formatter))
                .collect(Collectors.toList());
    }



    public static RoomMessageElementResponseDto of(ChatContent chatContent, String roomName, TriFunction<String, String, ChatUser> formatter) {
        ChatUser chatUser = formatter.apply(roomName, chatContent.getUserName());
        if (chatUser != null) {
            return RoomMessageElementResponseDto.builder()
                    .user(ChatUserResponseDto.of(chatUser))
                    .content(chatContent.getContent())
                    .time(chatContent.getTime().toString())
                    .build();
        } else {
            return RoomMessageElementResponseDto.builder()
                    .user(ChatUserResponseDto.builder().build())
                    .content(chatContent.getContent())
                    .time(chatContent.getTime().toString())
                    .build();
        }
    }

}

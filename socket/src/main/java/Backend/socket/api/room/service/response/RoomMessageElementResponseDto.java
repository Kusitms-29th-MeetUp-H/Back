package Backend.socket.api.room.service.response;

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
    private String image;

    public static List<RoomMessageElementResponseDto> listOf(List<ChatContent> chatContentList,String roomName,TriFunction<String, String, ChatUser> formatter, TriFunction<String,String,Boolean> function,String ownerSession) {
        return chatContentList.stream()
                .map(chatContent -> RoomMessageElementResponseDto.of(chatContent,roomName,formatter,function,ownerSession))
                .collect(Collectors.toList());
    }



    public static RoomMessageElementResponseDto of(ChatContent chatContent, String roomName, TriFunction<String, String, ChatUser> formatter,TriFunction<String,String,Boolean> function,String ownerSession) {
        ChatUser chatUser = formatter.apply(roomName, chatContent.getUserName());
        if (chatUser != null) {
            return RoomMessageElementResponseDto.builder()
                    .user(ChatUserResponseDto.of(chatUser,function.apply(ownerSession,chatUser.getSessionId())))
                    .content(chatContent.getContent())
                    .time(chatContent.getTime().toString())
                    .image(chatContent.getImage())
                    .build();
        } else {
            return RoomMessageElementResponseDto.builder()
                    .user(ChatUserResponseDto.builder().build())
                    .content(chatContent.getContent())
                    .time(chatContent.getTime().toString())
                    .image(chatContent.getImage())
                    .build();
        }
    }

}

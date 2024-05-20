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
public class ChatMessageElementResponseDto {
    private String userName;
    private String content;
    private String time;
    private String sessionId;
    private String profile;
    private String images;


//    public static List<ChatMessageElementResponseDto> listOf(List<ChatContent> chatContentList,String sessionId,String profile) {
//        return chatContentList.stream()
//                .map(chatContent -> ChatMessageElementResponseDto.of(chatContent, sessionId, profile))
//                .collect(Collectors.toList());
//    }


    public static ChatMessageElementResponseDto of(ChatContent chatContent, String sessionId,String profile, String image) {
        return ChatMessageElementResponseDto.builder()
                .userName(chatContent.getUserName())
                .content(chatContent.getContent())
                .time(chatContent.getTime().toString())
                .sessionId(sessionId)
                .profile(profile)
                .images(image)
                .build();
    }

}


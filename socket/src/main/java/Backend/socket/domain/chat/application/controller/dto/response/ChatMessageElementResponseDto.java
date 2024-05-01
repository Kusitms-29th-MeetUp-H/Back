package Backend.socket.domain.chat.application.controller.dto.response;

import Backend.socket.domain.chat.domain.ChatContent;
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

    public static List<ChatMessageElementResponseDto> listOf(List<ChatContent> chatContentList) {
        return chatContentList.stream()
                .map(ChatMessageElementResponseDto::of)
                .collect(Collectors.toList());
    }

    public static ChatMessageElementResponseDto of(ChatContent chatContent) {
        return ChatMessageElementResponseDto.builder()
                .userName(chatContent.getUserName())
                .content(chatContent.getContent())
                .time(chatContent.getTime().toString())
                .build();
    }
}

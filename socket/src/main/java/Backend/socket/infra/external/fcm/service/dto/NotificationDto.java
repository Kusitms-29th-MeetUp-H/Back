package Backend.socket.infra.external.fcm.service.dto;

import Backend.socket.infra.external.fcm.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private String id; //알림 받는 이
    private String str1; //알림 내역1
    private String str2; //알림 내역2
    /**
     * 커뮤니티 : 글이름 + 댓글단이 -> 글 Id, 댓글 Id
     * 일정 : 유저이름 + 싱크이름 -> 싱크 Id
     * 채팅방 개설 공지 : 싱크이름 -> 채팅방 Id
     *
     * TODO
     * 채팅 : 채팅내용 -> 채팅방 Id
     * 후기 : 유저이름 -> 마이페이지?
     */
    private MessageTemplate template;
    private String infoId;
    private String infoId2;
    private String channelId;

    /**
     *  user.getId(),
     *                     room.getRoomName(),
     *                     user.getUserName(),
     *                     MessageTemplate.CHAT,
     *                     chatMessage.getContent(),
     *                     room.getRoomSession(),
     *                     ""
     * @return
     */
    public static NotificationDto getChatMessageAlarm(Long userId, String roomName, Long fromUserId,
                                                      MessageTemplate template,
                                                      String roomSessionId,
                                                      String content) {
        return NotificationDto.builder()
                .id(userId.toString())
                .str1(roomName)
                .str2(fromUserId.toString())
                .template(template)
                .infoId(roomSessionId)
                .infoId2(content)
                .channelId("ChatChannel")
                .build();
    }

    public static NotificationDto getSyncReminderAlarm(Long userId, String userName, String syncName,
                                                       MessageTemplate template,
                                                       Long syncId) {
        return NotificationDto.builder()
                .id(userId.toString())
                .str1(userName)
                .str2(syncName)
                .template(template)
                .infoId(syncId.toString())
                .channelId("RemindChannel")
                .build();
    }

    public static NotificationDto getCommunityAlarm(Long userId, String postName, String userName,
                                                    MessageTemplate template,
                                                    Long postId, Long commendId) {
        return NotificationDto.builder()
                .id(userId.toString())
                .str1(postName)
                .str2(userName)
                .template(template)
                .infoId(postId.toString())
                .infoId2(commendId.toString())
                .channelId("CommunityChannel")
                .build();
    }

    public static NotificationDto getChatRoomNoticeAlarm(Long userId, String syncName,
                                                         MessageTemplate template,
                                                         String roomName) {

        return NotificationDto.builder()
                .id(userId.toString())
                .str1(syncName)
                .template(template)
                .infoId(roomName)
                .channelId("OpenChatChannel")
                .build();
    }
}
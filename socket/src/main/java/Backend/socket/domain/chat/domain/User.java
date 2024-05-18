package Backend.socket.domain.chat.domain;


import Backend.socket.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @Column(unique = true)
    private String platformId;
    private String email;
    private String userName;
    private String profile;
    private String refreshToken;
    private String sessionId;

    @Enumerated(EnumType.STRING)
    private Language language;
    private String university;
    private String nationality;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //일회성, 지속성, 내친소
    @Enumerated(EnumType.STRING)
    private SyncType syncType;



    private String languageLevel;
    //@ColumnDefault("0")
    //private int sync_cnt;

}


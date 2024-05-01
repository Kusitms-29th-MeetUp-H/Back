package Backend.socket.domain.chat.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String platformId;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    private String email;
    private String name;
    private String profile;
    private String refreshToken;
    private String sessionId;
}

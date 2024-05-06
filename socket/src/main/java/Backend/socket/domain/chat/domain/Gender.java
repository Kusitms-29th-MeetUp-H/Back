package Backend.socket.domain.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {

    MAN("man"),
    WOMAN("woman"),
    SECRET("secret");

    private final String gender;
}

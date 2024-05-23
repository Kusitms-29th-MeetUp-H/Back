package Backend.socket.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {
    KOREAN("korean"),
    ENGLISH("english");

    private final String language;
}

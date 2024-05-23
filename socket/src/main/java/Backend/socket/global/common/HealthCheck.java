package Backend.socket.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class HealthCheck {

    @GetMapping("/")
    public String MeetUpServer() {
        return "test";
    }

}

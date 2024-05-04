package Backend.socket.global.common;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @RequestMapping("/")
    public String MeetUpServer() {
        return "test";
    }
}

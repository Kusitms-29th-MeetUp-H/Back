package Backend.socket.global.common;

import Backend.socket.infra.external.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HealthCheck {
    private final AwsService awsService;
    @GetMapping("/")
    public String MeetUpServer() {
        return "test";
    }
    @PostMapping("/image")
    public String uploadImage(@RequestBody image imageDataString) {
        // 문자열을 공백을 기준으로 분리하여 문자열 배열로 변환
        String[] byteStrings = imageDataString.getImage().split(" ");

        // byte 배열 생성
        byte[] imageData = new byte[byteStrings.length];

        // 문자열 배열을 순회하면서 각 문자열을 byte로 변환하여 배열에 저장
        for (int i = 0; i < byteStrings.length; i++) {
            imageData[i] = (byte) Integer.parseInt(byteStrings[i], 16);
        }

        // 변환된 byte 배열을 사용하여 이미지 업로드
        String imageUrl = awsService.uploadImageToS3(imageData);
        return imageUrl;
    }
    @PostMapping("/images")
    public List<String> uploadImages(@RequestBody List<byte[]> imageDataList) {
        List<String> imageUrls = awsService.uploadImages(imageDataList);
        return imageUrls;
    }
}

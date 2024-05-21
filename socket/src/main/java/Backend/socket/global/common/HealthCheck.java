package Backend.socket.global.common;

import Backend.socket.infra.external.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Base64;
@RestController
@RequiredArgsConstructor
@RequestMapping
public class HealthCheck {
    private final AwsService awsService;
    @GetMapping("/")
    public String MeetUpServer() {
        return "test";
    }
    @GetMapping("/image")
    public String uploadImage(@RequestParam(name = "image") String image) throws IOException {
        String[] strings = image.split(" "); // ","을 기준으로 바이트 코드를 나눠준다
        String base64Image = strings[1];
        String extension = ""; // if 문을 통해 확장자명을 정해줌
        if (strings[0].equals("data:image/jpeg;base64")) {
            extension = "jpeg";
        } else if (strings[0].equals("data:image/png;base64")){
            extension = "png";
        } else {
            extension = "jpg";
        }


// ...

        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image); // 바이트 코드를  // 바이트 코드를

        File tempFile = File.createTempFile("image", "." + extension); // createTempFile을 통해 임시 파일을 생성해준다. (임시파일은 지워줘야함)
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(imageBytes); // OutputStream outputStream = new FileOutputStream(tempFile)을 통해 생성한 outputStream 객체에 imageBytes를 작성해준다.
        }
        // 문자열을 공백을 기준으로 분리하여 문자열 배열로 변환
        String[] byteStrings = image.split(" ");

        // byte 배열 생성
        byte[] imageData = new byte[byteStrings.length];

        for (int i = 0; i < byteStrings.length; i++) {
            if (byteStrings[i].matches("-?[0-9]+")) {
                imageData[i] = Byte.parseByte(byteStrings[i]);
            } else if (byteStrings[i].matches("-?0x[0-9a-fA-F]+")) {
                imageData[i] = (byte) Integer.parseInt(byteStrings[i].substring(2), 16);
            } else {
                // 잘못된 형식의 문자열인 경우 처리할 작업
                imageData[i] = 0;
            }
        }

        // 변환된 byte 배열을 사용하여 이미지 업로드
//        String imageUrl = awsService.uploadImageToS3(imageBytes);
        return null;
    }
    @PostMapping("/images")
    public List<String> uploadImages(@RequestBody List<byte[]> imageDataList) {
        List<String> imageUrls = awsService.uploadImages(imageDataList);
        return imageUrls;
    }

    @PostMapping("/test")
    public String uploadImagea(@RequestBody String image) throws IOException {
        int size = image.length();
        // 대괄호 제거 및 공백으로 구분
        String modifiedImageString = image.replaceAll("[\\[\\]]", "").replaceAll(",", " ");
//        System.out.println("Modified byte array: " + modifiedImageString);

        String imageUrl = awsService.uploadImageToS3(modifiedImageString);
        return imageUrl;
    }

    @GetMapping("/image/byte")
    public ResponseEntity<String> uploadImage(@RequestBody byte[] image) throws IOException {
        im
        // Base64 인코딩
        String base64EncodedString = Base64.getEncoder().encodeToString(image);
        int encodedSize = base64EncodedString.length();
        System.out.println("Base64 Encoded String: " + base64EncodedString);
        System.out.println("Encoded size: " + encodedSize + " bytes");

        // AWS S3 업로드
        String imageUrl = awsService.uploadImageToS3(base64EncodedString);

        return ResponseEntity.ok(imageUrl);
    }
}

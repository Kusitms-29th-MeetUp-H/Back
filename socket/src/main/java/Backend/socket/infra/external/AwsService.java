package Backend.socket.infra.external;


import Backend.socket.global.error.httpException.InternalServerException;
import Backend.socket.global.error.httpException.InvalidValueException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static Backend.socket.global.error.ErrorCode.INVALID_IMAGE_TYPE;
import static Backend.socket.global.error.ErrorCode.S3_UPLOAD_ERROR;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AwsService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String uploadImageToS3(String image) throws IOException {
        String[] strings = image.split("  "); // ","을 기준으로 바이트 코드를 나눠준다
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
        String[] byteStrings = image.split("  ");

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

        String fileName = UUID.randomUUID().toString();
        String fileUrl = "";

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");
            objectMetadata.setContentLength(imageData.length);

            InputStream inputStream = new ByteArrayInputStream(imageData);
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            fileUrl = amazonS3.getUrl(bucket, fileName).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUrl;
    }

    public List<String> uploadImages(List<byte[]> imageDataList) {
        if (imageDataList.isEmpty())
            return null;
        List<String> fileUrlList = new ArrayList<>();

        imageDataList.forEach(imageData -> {
            String fileName = UUID.randomUUID().toString();
            String fileUrl = "";

            try {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType("image/jpeg");
                objectMetadata.setContentLength(imageData.length);

                InputStream inputStream = new ByteArrayInputStream(imageData);
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                fileUrl = amazonS3.getUrl(bucket, fileName).toString();
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new InternalServerException(S3_UPLOAD_ERROR);
            }

            fileUrlList.add(fileUrl);
        });

        return fileUrlList;
    }
    public void deleteImage(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch(StringIndexOutOfBoundsException e) {
            throw new InvalidValueException(INVALID_IMAGE_TYPE);
        }
    }

}


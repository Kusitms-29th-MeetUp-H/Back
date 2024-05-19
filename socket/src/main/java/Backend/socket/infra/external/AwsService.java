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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    public String uploadImageToS3(byte[] imageData) {

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


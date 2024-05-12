package com.kusitms29.backendH.infra.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kusitms29.backendH.global.error.exception.InternalServerException;
import com.kusitms29.backendH.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String uploadImage(MultipartFile image) {
        String fileName = createFileName(image.getOriginalFilename());
        String fileUrl = amazonS3.getUrl(bucket, fileName).toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(image.getSize());
        objectMetadata.setContentType(image.getContentType());
        try {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, image.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new InternalServerException(S3_UPLOAD_ERROR);
        }
        return fileUrl;
    }

    public List<String> uploadImages(List<MultipartFile> images) {
        List<String> fileNameList = new ArrayList<>();
        List<String> fileUrlList = new ArrayList<>();

        images.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            String fileUrl = amazonS3.getUrl(bucket, fileName).toString();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(String.valueOf(file.getSize()));
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch(IOException e) {
                log.error(e.getMessage());
                throw new InternalServerException(S3_UPLOAD_ERROR);
            }
            fileNameList.add(fileName);
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

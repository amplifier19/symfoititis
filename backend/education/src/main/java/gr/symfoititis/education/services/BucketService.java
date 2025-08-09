package gr.symfoititis.education.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.ContentTooLargeException;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BucketService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3Client;

    public BucketService(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public List<String> listObjectsByCourseId(Integer c_id) {
        String objectsPath = String.format("notes/%d/", c_id);
        ObjectListing objects = amazonS3Client.listObjects(bucketName, objectsPath);
        return objects.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .filter(key -> !key.equals(objectsPath))
                .map(key -> key.replaceFirst(objectsPath, ""))
                .collect(Collectors.toList());
    }

    public String generateObjectPresignedUrl(int c_id, String objectName) {
        Date expiration = new Date();
        long milliSec = expiration.getTime() + 1000 * 60 * 20;
        expiration.setTime(milliSec);
        String path = String.format("notes/%d/%s", c_id, objectName);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest (bucketName, path)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private String uploadObject(Integer c_id, MultipartFile file) {
        String timestamp = Long.toString(Instant.now().getEpochSecond());
        String objectFullPath = String.format("notes/%d/%s-%s", c_id, timestamp, file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            if (file.getBytes().length > (50 * 1024 * 1024)) {
                throw new ContentTooLargeException("File size limit exceeded");
            }
            amazonS3Client.putObject(bucketName, objectFullPath, file.getInputStream(), metadata);
            return objectFullPath;
        } catch (MaxUploadSizeExceededException ex) {
            throw new BadRequestException("File size limit exceeded");
        } catch (IOException e) {
            throw new InternalServerErrorException("IO Exception");
        }
    }

    public List<String> uploadObjects(Integer c_id, MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files) {
            filenames.add(uploadObject(c_id, file));
        }
        return filenames;
    }

    public void deleteObject(Integer c_id, String objectName) {
        String objectFullPath = String.format("notes/%d/%s", c_id, objectName);
        amazonS3Client.deleteObject(bucketName, objectFullPath);
    }

}

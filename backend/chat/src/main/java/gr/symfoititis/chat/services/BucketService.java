package gr.symfoititis.chat.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
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

@Service
public class BucketService {
    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3Client;

    public BucketService (AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String generateObjectPresignedUrl(String roomId, String objectName) {
        Date expiration = new Date();
        long milliSec = expiration.getTime() + 1000 * 60 * 20;
        expiration.setTime(milliSec);
        String objectFullPath = String.format("rooms/%s/%s", roomId, objectName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest (bucketName, objectFullPath)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private String uploadObject(String roomId, MultipartFile file) {
        String timestamp = Long.toString(Instant.now().getEpochSecond());
        String originalFilename = file.getOriginalFilename();
        String filenameWithTimestamp = timestamp + '-' + originalFilename;
        String objectFullPath = String.format("rooms/%s/%s", roomId, filenameWithTimestamp);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            if (file.getBytes().length > (50 * 1024 * 1024)) {
                throw new ContentTooLargeException("File size limit exceeded");
            }
            amazonS3Client.putObject(bucketName, objectFullPath, file.getInputStream(), metadata);
            return filenameWithTimestamp;
        } catch (MaxUploadSizeExceededException ex) {
            throw new BadRequestException("File size limit exceeded");
        } catch (IOException e) {
            throw new InternalServerErrorException("IO Exception");
        }
    }

    public List<String> uploadObjects(String roomId, MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files) {
            filenames.add(uploadObject(roomId, file));
        }
        return filenames;
    }
}

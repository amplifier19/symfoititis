package gr.symfoititis.education.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import gr.symfoititis.common.exceptions.ContentTooLargeException;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilesService {
    @Value("${notes.directory}")
    private String notesDirectory;

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3Client;

    public FilesService (AmazonS3 amazonS3Client) {
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

    public String generateObjectPresignedUrl() {
        Date expiration = new Date();
        long milliSec = expiration.getTime() + 1000 * 60 * 20;
        expiration.setTime(milliSec);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest (bucketName, "notes/java-console.pdf")
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

//
    public List<String> getFiles (Integer c_id) {
        Path directoryPath = Path.of(notesDirectory).resolve(c_id.toString());
        List<String> filenames = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            for (Path file: stream) {
                if (Files.isRegularFile(file)) {
                    filenames.add(file.getFileName().toString());
                }
            }
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return filenames;
    }
    public String uploadOne (Integer c_id, MultipartFile file) {
        try {
            if (file.getBytes().length > (50 * 1024 * 1024)) {
                throw new ContentTooLargeException("File size limit exceeded");
            }
            if (!file.getOriginalFilename().matches("[a-zA-Z\\d]+(-[a-zA-Z\\d]+)*\\.[a-zA-Z]+")) {
                throw new BadRequestException ("Invalid file name");
            }
        } catch (MaxUploadSizeExceededException e) {
            throw new BadRequestException("File size limit exceeded");
        } catch (NullPointerException e) {
            throw new BadRequestException("Null file");
        }  catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        Path directoryPath = Path.of(notesDirectory).resolve(c_id.toString());
        String timestamp = Long.toString(Instant.now().getEpochSecond());
        String filename = timestamp + "-" + file.getOriginalFilename();
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Path filepath = directoryPath.resolve(filename);
            Files.copy(file.getInputStream(), filepath);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return filename;
    }
    public List<String> uploadMany (Integer c_id, MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file: files) {
            filenames.add(uploadOne (c_id, file));
        }
        return filenames;
    }

    public void deleteFile (Integer c_id, String filename) {
        Path directoryPath = Path.of(notesDirectory).resolve(c_id.toString());
        Path filepath = directoryPath.resolve(filename);
        try {
            if (Files.isRegularFile(filepath)) {
                Files.delete(filepath);
            } else {
                throw new NotFoundException("File note found");
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}

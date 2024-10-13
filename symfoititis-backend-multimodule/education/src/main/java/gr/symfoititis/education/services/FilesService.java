package gr.symfoititis.education.services;

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
import java.util.ArrayList;
import java.util.List;

@Service
public class FilesService {
    @Value("${notes.directory}")
    private String notesDirectory;

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

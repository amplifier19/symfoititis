package gr.symfoititis.admin.services;

import gr.symfoititis.admin.exceptions.ContentTooLargeException;
import gr.symfoititis.admin.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.common.services.CoursesService;
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
import java.util.Objects;

@Service
public class FilesService {
    @Value("${notes.directory}")
    private String notesDirectory;
    private final CoursesService commonCoursesService;

    public FilesService(CoursesService commonCoursesService) {
        this.commonCoursesService = commonCoursesService;
    }

    public List<String> getFiles (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <=0) {
            throw new BadRequestException("Bad Request");
        }
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
            /* Delete*/
        } catch (Exception e){
            throw e;
        }
        return filenames;
    }
    public String uploadOne (Integer c_id, MultipartFile file) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <=0) {
            throw new BadRequestException("Bad Request");
        }
        try {
            if (file.getBytes().length > (50 * 1024 * 1024)) {
                throw new ContentTooLargeException("File size limit exceeded");
            }
            if (!file.getOriginalFilename().matches("[a-zA-Z\\d]+(-[a-zA-Z\\d]+)*\\.[a-zA-Z]+")) {
                throw new BadRequestException ("Bad file name");
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
        commonCoursesService.getCourse(c_id);
        Path directoryPath = Path.of(notesDirectory).resolve(c_id.toString());
        String unixTime = Long.toString(Instant.now().getEpochSecond());
        String filename = unixTime + "-" + file.getOriginalFilename();
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
        if (Objects.isNull(c_id) || c_id.compareTo(0) <=0 || Objects.isNull(files) || files.length == 0 ) {
            throw new BadRequestException("Bad Request");
        }
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file: files) {
            filenames.add(uploadOne (c_id, file));
        }
        return filenames;
    }

    public void deleteFile (Integer c_id, String filename) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <=0 || filename.isBlank()) {
            throw new BadRequestException("Bad Request");
        }
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

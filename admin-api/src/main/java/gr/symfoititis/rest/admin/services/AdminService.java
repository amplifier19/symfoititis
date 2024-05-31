package gr.symfoititis.rest.admin.services;

import gr.symfoititis.rest.admin.dao.AdminDao;
import gr.symfoititis.rest.admin.exceptions.BadRequestException;
import gr.symfoititis.rest.admin.exceptions.ConflictException;
import gr.symfoititis.rest.admin.exceptions.InternalServerErrorException;
import gr.symfoititis.rest.admin.exceptions.NotFoundException;
import gr.symfoititis.rest.admin.records.Course;
import gr.symfoititis.rest.admin.records.Department;
import gr.symfoititis.rest.admin.records.Note;
import gr.symfoititis.rest.admin.records.University;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
public class AdminService {
    private final AdminDao adminDao;
    @Value("${notes.directory}")
    private String notesDirectory;
    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }


    /**
     *
     * Universities
     */
    public List<University> getUniversities () {
        return adminDao.getUniversities();
    }
    public University getUniversity (Integer uni_id) {
        if (Objects.isNull(uni_id) || uni_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return adminDao.getUniversity(uni_id).orElseThrow(() -> new NotFoundException("University Not Found"));
    }
    public void addUniversity (University university)  {
        if (
            Objects.isNull(university) ||
            university.uni_display_name().isBlank() ||
            university.uni_alt_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = adminDao.addUniversity(university);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DuplicateKeyException e) {
            throw new ConflictException ("Conflict");
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException ("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    public void updateUniversity (University university) {
        if (
            Objects.isNull(university) ||
            Objects.isNull(university.uni_id()) ||
            university.uni_id().compareTo(0) <= 0 ||
            university.uni_display_name().isBlank() ||
            university.uni_alt_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = adminDao.updateUniversity(university);
            if (status == 0 ) {
                throw new NotFoundException("University Not Found");
            }
        } catch (DuplicateKeyException e) {
            throw new ConflictException("Conflict");
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    public void deleteUniversity (Integer uni_id) {
        if (uni_id == null || uni_id.compareTo(0) <= 0) {
            throw new BadRequestException ("Bad Request");
        }
        int status = adminDao.deleteUniversity(uni_id);
        if (status == 0) {
            throw new NotFoundException("University Not Found");
        }
    }


    /**
     *
     * Departments
     */
    public List<Department> getDepartments () {
        return adminDao.getDepartments();
    }
    public List<Department> getDepartments (Integer uni_id) {
        if (Objects.isNull(uni_id) || uni_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return adminDao.getDepartments(uni_id);
    }
    public Department getDepartment (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return adminDao.getDepartment (dep_id).orElseThrow(() -> new NotFoundException("Department Not Found"));
    }
    public void addDepartment (Department department) {
        if (
            Objects.isNull(department) ||
            Objects.isNull(department.uni_id()) ||
            department.uni_id().compareTo(0) <= 0 ||
            department.dep_display_name().isBlank() ||
            department.dep_alt_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        getUniversity(department.uni_id());
        try {
            int status = adminDao.addDepartment(department);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    public void updateDepartment (Department department) {
        if (
            Objects.isNull(department) ||
            Objects.isNull(department.dep_id()) ||
            department.dep_id().compareTo(0) <= 0||
            Objects.isNull(department.uni_id()) ||
            department.uni_id().compareTo(0) <= 0 ||
            department.dep_display_name().isBlank() ||
            department.dep_alt_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        getUniversity(department.uni_id());
        try {
            int status = adminDao.updateDepartment(department);
            if (status == 0) {
                throw new NotFoundException("Department Not Found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    public void deleteDepartment (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException ("Bad Request");
        }
        int status = adminDao.deleteDepartment(dep_id);
        if (status == 0) {
            throw new NotFoundException("Department Not Found");
        }
    }


    /**
     *
     * Courses
     */
    public List<Course> getCourses (Integer dep_id) {
        if (Objects.isNull(dep_id) || dep_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return adminDao.getCourses(dep_id);
    }
    public Course getCourse (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return adminDao.getCourse(c_id).orElseThrow(() -> new NotFoundException("Course Not Found"));
    }
    public void addCourse (Course course) {
        if (
            Objects.isNull(course) ||
            Objects.isNull(course.dep_id()) ||
            course.dep_id().compareTo(0) <=0 ||
            Objects.isNull(course.semester()) ||
            course.c_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = adminDao.addCourse(course);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    public void updateCourse (Course course) {
        if (
            Objects.isNull(course) ||
            Objects.isNull(course.dep_id()) ||
            course.dep_id().compareTo(0) <=0 ||
            Objects.isNull(course.semester()) ||
            course.c_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        getDepartment(course.dep_id());
        try {
            int status = adminDao.updateCourse(course);
            if (status == 0) {
                throw new NotFoundException("Course Not Found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    public void deleteCourse (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <=0) {
            throw new BadRequestException("Bad Request");
        }
        int status = adminDao.deleteCourse(c_id);
        if (status == 0) {
            throw new NotFoundException("Course Not Found");
        }
    }


    /**
     *
     * Notes
     */
    public List<Note> getNotes (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return adminDao.getNotes(c_id);
    }
    public void addNote (Note note) {
        if (
            Objects.isNull(note) ||
            Objects.isNull(note.c_id()) ||
            note.c_id().compareTo(0) <= 0 ||
            note.type().isBlank() ||
            note.note_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = adminDao.addNote(note);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    public void updateNote (Note note) {
        if (
                Objects.isNull(note) ||
                Objects.isNull(note.note_id()) ||
                note.note_id().compareTo(0) <= 0 ||
                Objects.isNull(note.c_id()) ||
                note.c_id().compareTo(0) <= 0 ||
                note.type().isBlank() ||
                note.note_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = adminDao.updateNote(note);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public void deleteNote (Integer note_id) {
        if (Objects.isNull(note_id) || note_id.compareTo(0) <=0) {
            throw new BadRequestException("Bad Request");
        }
        int status = adminDao.deleteNote(note_id);
        if (status == 0) {
            throw new NotFoundException("Course Not Found");
        }
    }

    /**
     *
     * Files
     */
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
            if (file.getBytes().length > (1024 * 1024)) {
                throw new BadRequestException("File size limit exceeded");
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
        getCourse(c_id);
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
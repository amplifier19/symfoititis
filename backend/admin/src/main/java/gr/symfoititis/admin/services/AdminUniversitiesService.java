package gr.symfoititis.admin.services;

import gr.symfoititis.admin.dao.UniversitiesDao;
import gr.symfoititis.admin.exceptions.ConflictException;
import gr.symfoititis.admin.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.common.records.University;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UniversitiesService {
    private final UniversitiesDao universitiesDao;
    public UniversitiesService(UniversitiesDao universitiesDao) {
        this.universitiesDao = universitiesDao;
    }

    /**
     *
     * Universities
     */
    public void addUniversity (University university)  {
        if (
                Objects.isNull(university) ||
                        university.uni_display_name().isBlank() ||
                        university.uni_alt_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = universitiesDao.addUniversity(university);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DuplicateKeyException e) {
            throw new ConflictException("Conflict");
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
            int status = universitiesDao.updateUniversity(university);
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
        int status = universitiesDao.deleteUniversity(uni_id);
        if (status == 0) {
            throw new NotFoundException("University Not Found");
        }
    }
}

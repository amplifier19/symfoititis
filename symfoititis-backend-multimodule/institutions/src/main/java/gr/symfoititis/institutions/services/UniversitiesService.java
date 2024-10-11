package gr.symfoititis.institutions.services;

import gr.symfoititis.institutions.dao.UniversitiesDao;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.ConflictException;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import gr.symfoititis.institutions.records.University;

import java.util.List;
import java.util.Objects;

@Service
public class UniversitiesService {
    protected final UniversitiesDao universitiesDao;

    public UniversitiesService(UniversitiesDao studentDao) {
        this.universitiesDao = studentDao;
    }

    public List<University> getUniversities () {
        return universitiesDao.getUniversities();
    }
    public University getUniversity (Integer uni_id) {
        if (Objects.isNull(uni_id) || uni_id <=0) {
            throw new BadRequestException("Bad Request");
        }
        return universitiesDao.getUniversity(uni_id).orElseThrow(() -> new NotFoundException("University Not Found"));
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
            universitiesDao.addUniversity(university);
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

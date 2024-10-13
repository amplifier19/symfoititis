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
        return universitiesDao.getUniversity(uni_id).orElseThrow(() -> new NotFoundException("University Not Found"));
    }

    public void addUniversity (University university)  {
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
        int status = universitiesDao.deleteUniversity(uni_id);
        if (status == 0) {
            throw new NotFoundException("University Not Found");
        }
    }
}

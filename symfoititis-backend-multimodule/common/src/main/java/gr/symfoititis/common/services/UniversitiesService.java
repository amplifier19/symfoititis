package gr.symfoititis.common.services;

import gr.symfoititis.common.dao.UniversitiesDao;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.common.records.University;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UniversitiesService {
    protected final UniversitiesDao universitiesDao;

    public UniversitiesService(UniversitiesDao studentDao) {
        this.universitiesDao = studentDao;
    }

    /**
     *
     * Universities
     */
    public List<University> getUniversities () {
        return universitiesDao.getUniversities();
    }
    public University getUniversity (Integer uni_id) {
        if (Objects.isNull(uni_id) || uni_id <=0) {
            throw new BadRequestException("Bad Request");
        }
        return universitiesDao.getUniversity(uni_id).orElseThrow(() -> new NotFoundException("University Not Found"));
    }
}

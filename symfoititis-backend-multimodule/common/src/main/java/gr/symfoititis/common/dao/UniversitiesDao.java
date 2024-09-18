package gr.symfoititis.common.dao;

import gr.symfoititis.common.records.University;

import java.util.List;
import java.util.Optional;

public interface UniversitiesDao {

    /**
     *
     * Universities
     */
    List<University> getUniversities ();
    Optional<University> getUniversity (Integer uni_id);
}

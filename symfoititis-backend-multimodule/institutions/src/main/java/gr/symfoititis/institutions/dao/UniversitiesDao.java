package gr.symfoititis.institutions.dao;


import gr.symfoititis.institutions.records.University;

import java.util.List;
import java.util.Optional;

public interface UniversitiesDao {
    List<University> getUniversities ();
    Optional<University> getUniversity (Integer uni_id);
    void addUniversity (University university);
    int updateUniversity (University university);
    int deleteUniversity (Integer uni_id);
}

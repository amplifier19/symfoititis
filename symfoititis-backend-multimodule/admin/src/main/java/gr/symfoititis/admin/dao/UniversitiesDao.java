package gr.symfoititis.admin.dao;

import gr.symfoititis.common.records.University;

public interface UniversitiesDao {

    /**
     *
     * Universities
     */
    int addUniversity (University university);
    int updateUniversity (University university);
    int deleteUniversity (Integer uni_id);
}

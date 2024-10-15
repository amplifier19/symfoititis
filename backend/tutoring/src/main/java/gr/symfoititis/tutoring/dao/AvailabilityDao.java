package gr.symfoititis.tutoring.dao;

import gr.symfoititis.tutoring.entities.AvailabilitySlot;

import java.util.List;

public interface AvailabilityDao {
    List<AvailabilitySlot> getAvailabilitySlots (Integer c_id, String t_id);
    List<String> getAvailableTeacherIds (Integer c_id, Integer dep_id);
    void addAvailabilitySlots (List<AvailabilitySlot> availabilitySlots);
    int[] updateAvailabilitySlots (List<AvailabilitySlot> availabilitySlots);
    int[] deleteAvailabilitySlots (List<Integer> av_id, String t_id);
}

package gr.symfoititis.tutoring.dao;

import gr.symfoititis.tutoring.records.AvailabilitySlot;

import java.util.List;
import java.util.Optional;

public interface AvailabilityDao {
    List<AvailabilitySlot> getAvailabilitySlots (Integer dep_id, Integer c_id, String t_id);
    List<AvailabilitySlot> getTeacherAvailabilitySlots (Integer dep_id, Integer c_id, String t_id);
    List<AvailabilitySlot> getAvailabilitySlotsByIds (List<Integer> av_id, Integer dep_id);
    List<String> getAvailableTeacherIds (Integer dep_id, Integer c_id);
    List<Integer> getAvailableTutoringCourseIds (Integer dep_id);
    void addAvailabilitySlots (List<AvailabilitySlot> availabilitySlots);
    int[] updateAvailabilitySlots (List<AvailabilitySlot> availabilitySlots);
    int[] deleteAvailabilitySlots (List<Integer> av_id, Integer dep_id, String t_id);
}

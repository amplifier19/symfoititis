package gr.symfoititis.tutoring.services;

import gr.symfoititis.common.entities.Teacher;
import gr.symfoititis.teacher.services.TeacherService;
import gr.symfoititis.tutoring.dao.AvailabilityDao;
import gr.symfoititis.tutoring.entities.AvailabilitySlot;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AvailabilityService {
    private final AvailabilityDao availabilityDao;
    private final TeacherService teacherService;

    public AvailabilityService(AvailabilityDao availabilityDao, TeacherService teacherService) {
        this.availabilityDao = availabilityDao;
        this.teacherService = teacherService;
    }

    public List<AvailabilitySlot> getAvailabilitySlots (Integer c_id, String t_id) {
        return availabilityDao.getAvailabilitySlots(c_id, t_id);
    }

    public List<Teacher> getAvailableTeachers(Integer c_id, Integer dep_id) {
        List<String> teacherIds = availabilityDao.getAvailableTeacherIds(c_id, dep_id);
        return teacherIds.stream()
                .map(teacherService::getTeacher)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAvailabilitySlots (List<AvailabilitySlot> availabilitySlots) {
        availabilityDao.addAvailabilitySlots(availabilitySlots);
    }

    @Transactional
    public void updateAvailabilitySlots (List<AvailabilitySlot> availabilitySlots) {
        availabilityDao.updateAvailabilitySlots(availabilitySlots);
    }

    @Transactional
    public void deleteAvailabilitySlots (List<Integer> availabilitySlotIds, String t_id) {
        availabilityDao.deleteAvailabilitySlots(availabilitySlotIds, t_id);
    }
}

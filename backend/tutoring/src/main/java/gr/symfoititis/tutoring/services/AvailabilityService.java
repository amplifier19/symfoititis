package gr.symfoititis.tutoring.services;

import gr.symfoititis.common.entities.Teacher;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.teacher.services.TeacherService;
import gr.symfoititis.tutoring.dao.AvailabilityDao;
import gr.symfoititis.tutoring.records.AvailabilitySlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private final AvailabilityDao availabilityDao;
    private final TeacherService teacherService;

    public AvailabilityService(AvailabilityDao availabilityDao, TeacherService teacherService) {
        this.availabilityDao = availabilityDao;
        this.teacherService = teacherService;
    }

    public List<AvailabilitySlot> getAvailabilitySlots(Integer dep_id, Integer c_id, String t_id) {
        return availabilityDao.getAvailabilitySlots(dep_id, c_id, t_id);
    }

    public AvailabilitySlot getAvailabilitySlot(Integer av_id, Integer dep_id) {
        return availabilityDao.getAvailabilitySlot(av_id, dep_id).orElseThrow(() ->
                new NotFoundException("Availability slot not found")
        );
    }

    public List<Teacher> getAvailableTeachers(Integer dep_id, Integer c_id) {
        List<String> teacherIds = availabilityDao.getAvailableTeacherIds(dep_id, c_id);
        return teacherIds.stream()
                .map(teacherService::getTeacher)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addAvailabilitySlots(List<AvailabilitySlot> availabilitySlots, Integer dep_id, String t_id) {
        availabilitySlots.forEach(slot -> {
            if (slot.dep_id() != dep_id) {
                throw new BadRequestException("Invalid Department id");
            }
            if (!slot.t_id().equals(t_id)) {
                throw new BadRequestException("Invalid teacher id");
            }
        });
        availabilityDao.addAvailabilitySlots(availabilitySlots);
    }

    @Transactional
    public void updateAvailabilitySlots(List<AvailabilitySlot> availabilitySlots, Integer dep_id, String t_id) {
        availabilitySlots.forEach(slot -> {
            if (slot.av_id() == null) {
                throw new BadRequestException("Availability slot id cannot be null");
            }
            if (slot.dep_id() != dep_id) {
                throw new BadRequestException("Invalid Department id");
            }
            if (!slot.t_id().equals(t_id)) {
                throw new BadRequestException("Invalid teacher id");
            }
        });
        availabilityDao.updateAvailabilitySlots(availabilitySlots);
    }

    @Transactional
    public void deleteAvailabilitySlots(List<Integer> availabilitySlotIds, Integer dep_id, String t_id) {
        availabilityDao.deleteAvailabilitySlots(availabilitySlotIds, dep_id, t_id);
    }
}

package gr.symfoititis.tutoring.services;

import gr.symfoititis.tutoring.entities.Booking;
import gr.symfoititis.tutoring.entities.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    public Set<String> getUniqueTeacherIds (List<Booking> bookings) {
        return bookings.stream().map(Booking::getTeacherId).collect(Collectors.toSet());
    }

    public Teacher getTeacher (String t_id) {
        return new Teacher (t_id, "john",  "doe");
    }
}

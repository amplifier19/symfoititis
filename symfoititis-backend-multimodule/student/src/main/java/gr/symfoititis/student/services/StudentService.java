package gr.symfoititis.student.services;

import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    public Set<String> getUniqueStudentIds (List<Booking> bookings) {
        return bookings.stream().map(Booking::getStudentId).collect(Collectors.toSet());
    }

    public Student getStudent (String s_id) {
        return new Student(s_id, "amplifier");
    }
}

package gr.symfoititis.student.records;

import java.time.LocalDate;



public record AvailabilitySlot(int av_id, int c_id, char t_id, LocalDate date, int week_day, int start_time, SlotState state) { }
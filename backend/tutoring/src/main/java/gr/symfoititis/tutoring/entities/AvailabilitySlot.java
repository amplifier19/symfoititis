package gr.symfoititis.tutoring.records;

import gr.symfoititis.tutoring.enums.SlotState;

import java.time.LocalDate;


public record AvailabilitySlot(Integer av_id, Integer dep_id, Integer c_id, String t_id, LocalDate date, Integer week_day, Integer start_time, SlotState state) { }
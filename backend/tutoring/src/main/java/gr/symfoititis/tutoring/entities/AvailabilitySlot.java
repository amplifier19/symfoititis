package gr.symfoititis.tutoring.entities;

import gr.symfoititis.tutoring.enums.SlotState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;


public record AvailabilitySlot(
        @Positive(message = "Availability slot id must be a positive value")
        Integer av_id,
        @NotNull(message = "Department id cannot be null")
        @Positive(message = "Department id must be a positive value")
        Integer dep_id,
        @NotNull(message = "Course id cannot be null")
        @Positive(message = "Course id must be a positive value")
        Integer c_id,
        @NotNull(message = "Teacher id cannot be null")
        @NotBlank(message = "Teacher id cannot be blank")
        String t_id,
        @NotNull(message = "Availability Date cannot be null")
        LocalDate date,
        @NotNull(message = "Week day cannot be null")
        @PositiveOrZero(message = "Week day must be a non negative value")
        Integer week_day,

        @NotNull(message = "Start time cannot be null")
        @Positive(message = "Start time must be a positive value")
        Integer start_time,
        @NotNull(message = "Slot state cannot be null")
        SlotState state
) { }
package gr.symfoititis.tutoring.records;

import gr.symfoititis.tutoring.enums.SlotState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;


public record AvailabilitySlot(
        @Positive
        Integer av_id,
        @NotNull
        @Positive
        Integer dep_id,
        @NotNull
        @Positive
        Integer c_id,
        @NotNull
        @NotBlank
        String t_id,
        @NotNull
        LocalDate date,
        @NotNull
        @PositiveOrZero
        Integer week_day,
        @NotNull
        @Positive
        Integer start_time,
        SlotState state
) { }
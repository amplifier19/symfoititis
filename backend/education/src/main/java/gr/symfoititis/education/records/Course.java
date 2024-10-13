package gr.symfoititis.education.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Course(
        @Positive(message = "Course id must be positive")
        Integer c_id,
        @NotNull(message = "Department id cannot be null")
        @Positive(message = "Department id must be positive")
        Integer dep_id,
        @NotNull(message = "Semester cannot be null")
        @Positive(message = "Semester must be positive")
        Integer semester,
        @NotNull(message = "Course display name cannot be null")
        @NotBlank(message = "Course display name cannot be blank")
        String c_display_name,
        String description
) {}
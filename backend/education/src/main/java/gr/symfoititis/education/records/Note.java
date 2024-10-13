package gr.symfoititis.education.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Note(
        @Positive(message = "Note id must be positive")
        Integer note_id,
        @NotNull(message = "Course id cannot be null")
        @Positive(message = "Course id must be positive")
        Integer c_id,
        @NotNull(message = "Note type cannot be null")
        @NotBlank(message = "Note type cannot be blank")
        String type,
        @NotNull(message = "Note display name cannot be null")
        @NotBlank(message = "Note display name cannot be blank")
        String note_display_name,
        @NotNull(message = "Note file name cannot be null")
        @NotBlank(message = "Note file name cannot be blank")
        String note_filename
) {}

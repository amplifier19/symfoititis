package gr.symfoititis.tutoring.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class StudentBalance {
    @Positive
    private  Integer id;
    @NotBlank
    private String student_id;
    @NotNull
    @PositiveOrZero
    private Integer hours;
    @NotNull
    @PositiveOrZero
    private Integer weight;

    public StudentBalance(
            @NotBlank
            String student_id,
            @NotNull
            @PositiveOrZero
            Integer hours,
            @NotNull
            @PositiveOrZero
            Integer weight
    ) {
        this.student_id = student_id;
        this.hours = hours;
        this.weight = weight;
    }

    public StudentBalance(
            @NotNull
            @Positive
            Integer id,
            @NotBlank
            String student_id,
            @NotNull
            @PositiveOrZero
            Integer hours,
            @NotNull
            @PositiveOrZero
            Integer weight
    ) {
        this.id = id;
        this.student_id = student_id;
        this.hours = hours;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return student_id;
    }

    public void setStudentId(String student_id) {
        this.student_id = student_id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}

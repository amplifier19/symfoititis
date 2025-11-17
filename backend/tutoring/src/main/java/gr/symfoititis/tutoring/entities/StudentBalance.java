package gr.symfoititis.tutoring.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class StudentBalance {
    @Positive
    private  Integer id;
    @NotBlank
    private String studentId;
    @NotNull
    @PositiveOrZero
    private Integer hours;
    @NotNull
    private Boolean isPremium;

    public StudentBalance(
            @NotBlank
            String studentId,
            @PositiveOrZero
            Integer hours,
            @NotNull
            Boolean isPremium
    ) {
        this.studentId = studentId;
        this.hours = hours;
        this.isPremium = isPremium;
    }

    public StudentBalance(
            @NotNull
            @Positive
            Integer id,
            @NotBlank
            String studentId,
            @PositiveOrZero
            Integer hours,
            @NotNull
            Boolean isPremium
    ) {
        this.id = id;
        this.studentId = studentId;
        this.hours = hours;
        this.isPremium = isPremium;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

}

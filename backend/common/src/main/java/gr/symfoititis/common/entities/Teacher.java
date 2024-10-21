package gr.symfoititis.common.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teacher {
    private String t_id;
    @NotNull
    @NotBlank
    private String firstname;
    @NotNull
    @NotBlank
    private String lastname;
    public Teacher() {}

    public Teacher(
            @NotNull
            @NotBlank
            String t_id,
            String firstname,
            String lastname
    ) {
        this.t_id = t_id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Teacher(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getT_id() {
        return t_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}

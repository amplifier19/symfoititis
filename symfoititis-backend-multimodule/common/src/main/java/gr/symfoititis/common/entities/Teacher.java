package gr.symfoititis.tutoring.entities;

public class Teacher {
    private String t_id;
    private String first_name;
    private String last_name;
    public Teacher() {
    }

    public Teacher (String t_id, String first_name, String last_name) {
        this.t_id = t_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void setTeacherId(String t_id) {
        this.t_id = t_id;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }
}

package gr.symfoititis.common.entities;

public class Student {
    private String s_id;
    private String student_name;
    private Integer tokens;

    public Student() {}

    public Student(String s_id, String student_name) {
        this.s_id = s_id;
        this.student_name = student_name;
    }

    public String getStudentId() {
        return s_id;
    }

    public void setStudentId(String s_id) {
        this.s_id = s_id;
    }

    public String getStudentName() {
        return student_name;
    }

    public void setStudentName(String student_name) {
        this.student_name = student_name;
    }

    public Integer getTokens() {
        return tokens;
    }
    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }
}

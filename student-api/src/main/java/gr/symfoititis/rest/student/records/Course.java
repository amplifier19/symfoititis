package gr.symfoititis.rest.student.records;

public record Course(Integer c_id, Integer dep_id, Integer semester, String c_display_name, String description) {}
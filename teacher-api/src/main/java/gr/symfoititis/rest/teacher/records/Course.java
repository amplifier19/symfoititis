package gr.symfoititis.rest.teacher.records;

public record Course(Integer c_id, Integer dep_id, Integer semester, String c_display_name, String description) {}
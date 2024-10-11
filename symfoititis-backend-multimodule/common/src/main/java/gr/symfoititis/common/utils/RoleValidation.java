package gr.symfoititis.common.utils;

import gr.symfoititis.common.exceptions.ForbiddenException;

public class RoleValidation {
    public enum Role {
        student,
        teacher,
        admin
    }
    public static void isAdmin(String role) {
        if (Role.admin.toString().equals(role)) return;
        throw new ForbiddenException("Forbidden");
    }
    public static void isStudent(String role) {
        if (Role.student.toString().equals(role)) return;
        throw new ForbiddenException("Forbidden");
    }
    public static void isTeacher(String role) {
        if (Role.teacher.toString().equals(role)) return;
        throw new ForbiddenException("Forbidden");
    }
    public static void isStudentOrTeacher(String role) {
        if (Role.student.toString().equals(role) || Role.teacher.toString().equals(role)) return;
        throw new ForbiddenException("Forbidden");
    }
    public static void isAnyone(String role) {
        if (
                Role.student.toString().equals(role) ||
                Role.teacher.toString().equals(role) ||
                Role.admin.toString().equals(role)
        ) return;
        throw new ForbiddenException("Forbidden");
    }
}

package dao;

import student.Student;

import java.io.IOException;


public interface StudentDao {
    String saveStudent(Student student) throws IOException;

    String removeStudent(String studentId) throws IOException;

    Student getStudent(String studentId);
}

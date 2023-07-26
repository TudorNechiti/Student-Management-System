package com.tuds.sss.student;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.selectAllStudents();
    }

    public void addNewStudent(Student student) {
       addNewStudent(null, student);
    }

    public void addNewStudent(UUID studentID, Student student) {
        UUID newStudentID = Optional.ofNullable(studentID).orElse(UUID.randomUUID());
        studentRepository.insertStudent(newStudentID, student);
    }
}

package com.tuds.sss.student;

import com.tuds.sss.EmailValidator;
import com.tuds.sss.exception.ApiRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final EmailValidator emailValidator;

    public StudentService(StudentRepository studentRepository, EmailValidator emailValidator) {
        this.studentRepository = studentRepository;
        this.emailValidator = emailValidator;
    }

    public List<Student> getAllStudents() {
        return studentRepository.selectAllStudents();
    }

    public void addNewStudent(Student student) {
        addNewStudent(null, student);
    }

    public void addNewStudent(UUID studentID, Student student) {
        UUID newStudentID = Optional.ofNullable(studentID).orElse(UUID.randomUUID());
        if(!emailValidator.test(student.getEmail()))
            throw new ApiRequestException(student.getEmail() + " is not valid!");

        if(studentRepository.isEmailTaken(student.getEmail())){
            throw new ApiRequestException(student.getEmail() + " already taken!");
        }

        studentRepository.insertStudent(newStudentID, student);
    }


    public List<StudentCourse> getAllCoursesForStudent(UUID studentID) {
        return studentRepository.selectAllCoursesForStudent(studentID);
    }

    public void deleteStudent(UUID studentID) {
        studentRepository.deleteStudentInstance(studentID);
    }
}

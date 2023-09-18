package com.tuds.sss;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tuds.sss.EmailValidator;
import com.tuds.sss.exception.ApiRequestException;
import com.tuds.sss.student.Student;
import com.tuds.sss.student.StudentRepository;
import com.tuds.sss.student.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EmailValidator emailValidator;

    @InjectMocks //injects @Mock annotated instances into studentService
    private StudentService studentService;

    @Test
    void getAllStudents() {
        // Mocking behavior
        when(studentRepository.selectAllStudents()).thenReturn(List.of());

        // Test
        List<Student> students = studentService.getAllStudents();

        // Assertions
        assertNotNull(students);
    }

    @Test
    void addNewStudent_validEmail() {
        // Mocking behavior
        Student student =  new Student(UUID.randomUUID(), "John", "Doe", "john@example.com", Student.Gender.MALE);
        when(emailValidator.test(student.getEmail())).thenReturn(true);
        when(studentRepository.isEmailTaken(student.getEmail())).thenReturn(false);

        assertDoesNotThrow(() -> studentService.addNewStudent(student));
    }

    @Test
    void addNewStudent_invalidEmail() {
        // Mocking behavior
        Student student =  new Student(UUID.randomUUID(), "John", "Doe", "johnxsxsacom", Student.Gender.MALE);
        when(emailValidator.test(student.getEmail())).thenReturn(false);

        assertThrows(ApiRequestException.class, () -> studentService.addNewStudent(student));
    }

    @Test
    void addNewStudent_emailAlreadyTaken() {
        // Mocking behavior
        Student student =
                new Student(UUID.randomUUID(), "John", "Doe", "john@example.com", Student.Gender.MALE);
        when(emailValidator.test(student.getEmail())).thenReturn(true);
        when(studentRepository.isEmailTaken(student.getEmail())).thenReturn(true);

        // Test
        assertThrows(ApiRequestException.class, () -> studentService.addNewStudent(student));
    }



}


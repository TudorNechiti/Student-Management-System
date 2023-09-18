package com.tuds.sss;

import com.tuds.sss.student.Student;
import com.tuds.sss.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentRepository = new StudentRepository(jdbcTemplate);
    }

    @Test
    void selectAllStudents() {
        // Setup mock behavior
        List<Student> expectedStudents = List.of(
                new Student(UUID.randomUUID(), "John", "Doe", "john@example.com", Student.Gender.MALE),
                new Student(UUID.randomUUID(), "Jane", "Doe", "jane@example.com", Student.Gender.FEMALE)
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedStudents);

        // Test
        List<Student> actualStudents = studentRepository.selectAllStudents();

        // Verify
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void deleteStudentInstance() {
        // Arrange
        UUID studentID = UUID.randomUUID();
        Student mockStudent = new Student(studentID, "John", "Doe", "john@example.com", Student.Gender.MALE);

        when(jdbcTemplate.update(eq("DELETE FROM students WHERE student_id = ?"), eq(studentID))).thenReturn(1);

        // Act
        studentRepository.insertStudent(studentID, mockStudent); // Insert the mock student
        studentRepository.deleteStudentInstance(studentID); // Delete the student

        // Assert
        verify(jdbcTemplate, times(1)).update(anyString(), eq(studentID));
    }

}

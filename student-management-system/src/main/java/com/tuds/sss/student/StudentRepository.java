package com.tuds.sss.student;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> selectAllStudents() {
        String sql = "" +
                "SELECT student_id, " +
                "first_name, " +
                "last_name, " +
                "email, " +
                "gender FROM students";

        return jdbcTemplate.query(sql, mapStudentFromDb());
    }

    private RowMapper<Student> mapStudentFromDb(){
        return (resultSet, i) -> {
            UUID studentID = UUID.fromString(resultSet.getString("student_id"));
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            Student.Gender gender = Student.Gender.valueOf(resultSet.getString("gender").toUpperCase());
            return new Student(studentID, firstName, lastName, email, gender);
        };
    }

    public int insertStudent(UUID studentID, Student student) {
        String sql = "" +
                "INSERT INTO students (student_id, first_name, last_name, email, gender)" +
                "VALUES (?,?,?,?,?::gender)";

        return jdbcTemplate.update(sql,
                studentID,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getGender().name().toUpperCase());
    }
}

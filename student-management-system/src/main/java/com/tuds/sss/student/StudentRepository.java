package com.tuds.sss.student;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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

    @SuppressWarnings("ConstantConditions")
    public boolean isEmailTaken(String email){
        String sql = "" +
                "SELECT EXISTS(" +
                "SELECT 1 " +
                "FROM students " +
                "WHERE email = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getBoolean(1), email));
    }

    public List<StudentCourse> selectAllCoursesForStudent(UUID studentId) {
        String sql = "" +
                " SELECT students.student_id, course.course_id, course.name, course.description, course.department, " +
                " course.teacher_name, student_course.start_date, student_course.end_date, student_course.grade " +
                " FROM students " +
                " JOIN student_course USING (student_id)" +
                " JOIN course using (course_id)" +
                " WHERE students.student_id = ?";

        return jdbcTemplate.query(sql, mapStudentCourseFromDb(), studentId);
    }

    private RowMapper<StudentCourse> mapStudentCourseFromDb() {
        return (resultSet, i) ->
                new StudentCourse(
                        UUID.fromString(resultSet.getString("student_id")),
                        UUID.fromString(resultSet.getString("course_id")),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"),
                        resultSet.getString("teacher_name"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        Optional.ofNullable(resultSet.getString("grade")).map(Integer::parseInt).orElse(null)
                );
    }

    public void deleteStudentInstance(UUID studentID) {
        // First, delete student_course records for the student
        String deleteStudentCoursesSQL = "" +
                "DELETE FROM student_course " +
                "WHERE student_id = ?";
        jdbcTemplate.update(deleteStudentCoursesSQL, studentID);

        // Then, delete the student from students table
        String deleteStudentSQL = "" +
                "DELETE FROM students " +
                "WHERE student_id = ?";
        jdbcTemplate.update(deleteStudentSQL, studentID);
    }
}

package com.tuds.sss.student;

import java.time.LocalDate;
import java.util.UUID;

public class StudentCourse {

    private final UUID studentID;
    private final UUID courseID;
    private final String name;
    private final String description;
    private final String department;
    private final String teacherName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer grade;

    public StudentCourse(UUID studentID, UUID courseID, String name, String description,
                         String department, String teacherName, LocalDate startDate, LocalDate endDate, Integer grade) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.name = name;
        this.description = description;
        this.department = department;
        this.teacherName = teacherName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;
    }

    public UUID getStudentID() {
        return studentID;
    }

    public UUID getCourseID() {
        return courseID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public String getTeacherName() {
        return teacherName;
    }
}

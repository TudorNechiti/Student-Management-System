package com.tuds.sss.student;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("students")
public class StudentController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Student> getAllStudents() {
        return List.of(
                new Student(UUID.randomUUID(), "Tudor", "Nechiti", "nechititudorr@gmail.com", Student.Gender.MALE)
        );
    }
}

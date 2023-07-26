package com.tuds.sss.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Student> getAllStudents() {
       return studentService.getAllStudents();
    }

    @PostMapping
    public void addNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }
}

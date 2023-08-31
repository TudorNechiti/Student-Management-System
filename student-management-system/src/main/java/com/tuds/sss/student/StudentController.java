package com.tuds.sss.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents()  {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "{studentID}/courses")
    public List<StudentCourse> getAllCoursesForStudent(@PathVariable("studentID") UUID studentID){
        return studentService.getAllCoursesForStudent(studentID);
    }

    @PostMapping
    public void addNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "/{studentID}")
    public void deleteStudent(@PathVariable("studentID") UUID studentID){
        studentService.deleteStudent(studentID);
    }
}

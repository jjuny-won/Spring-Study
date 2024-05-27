package JPA_Study.JPA_Study.controller;

import JPA_Study.JPA_Study.Service.CourseService;
import JPA_Study.JPA_Study.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/print")
    public void printCoursesWithStudents() {
        courseService.printAllCoursesWithStudents();
    }
}

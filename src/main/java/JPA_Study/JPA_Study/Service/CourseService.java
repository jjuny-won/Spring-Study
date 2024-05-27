package JPA_Study.JPA_Study.Service;

import JPA_Study.JPA_Study.Repository.CourseRepository;
import JPA_Study.JPA_Study.entity.Course;
import JPA_Study.JPA_Study.entity.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    public void printAllCoursesWithStudents() {
        List<Course> courses = courseRepository.findAll();

        for (Course c : courses) {
            System.out.println("Course " + c.getName() + " has students: ");
            for (Student s : c.getStudents()) {
                System.out.println(" - " + s.getName());
            }
        }
    }
}

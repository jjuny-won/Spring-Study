package JPA_Study.JPA_Study.Service;

import JPA_Study.JPA_Study.Repository.CourseRepository;
import JPA_Study.JPA_Study.entity.Course;
import JPA_Study.JPA_Study.entity.Student;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    @Transactional(readOnly = true)
    public void printAllCoursesWithStudents() {
        List<Course> courses = courseRepository.findAll();// 지연 로딩 설정

        for (Course c : courses) {
            System.out.println("Course " + c.getName() + " has students: ");
            for (Student s : c.getStudents()) {
                System.out.println(" - " + s.getName());
            }
        }
    }
    @Transactional(readOnly = true)
    public List<Course> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            course.getStudents().size();  // Initialize students collection
            course.getMaterials().size();  // Initialize materials collection
        }
        return courses;
    }
}

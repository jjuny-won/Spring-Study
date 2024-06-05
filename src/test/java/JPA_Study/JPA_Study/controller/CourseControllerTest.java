package JPA_Study.JPA_Study.controller;

import JPA_Study.JPA_Study.Repository.CourseRepository;
import JPA_Study.JPA_Study.Repository.StudentRepository;
import JPA_Study.JPA_Study.Service.CourseService;
import JPA_Study.JPA_Study.entity.Course;

import JPA_Study.JPA_Study.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Eager type은 Course 검색 시 조인을 통해 학생들도 같이 로딩한다.")
    void usingSinglindTest() {
        System.out.println("== start ==");
        Course course = courseRepository.findById(1L)
                .orElseThrow(RuntimeException::new);
        System.out.println("== end ==");
        System.out.println(course.getName());
    }

    //EagerType 에서 N+1 문제 발생
    @Test
    @DisplayName("Eager type은 Course를 전체 검색할 때 N+1문제가 발생한다.")
    void courseFindTest() {
        System.out.println("== start ==");
        List<Course> courses = courseRepository.findAll();
        System.out.println("== find all ==");

    }

    @Test
    @Transactional
    @DisplayName("프록시 초기화 테스트")
    void courseProxyTest() {
        System.out.println("== start ==");
        Optional<Student> student = studentRepository.findById(3L);
        Course course = student.get().getCourse(); // 프록시 초기화
        System.out.println("course.getId() = " + course.getId()); //프록시 객체가 제공하는 ID를 반환
        System.out.println("course.getName() = " + course.getName()); // 이 시점에서 프록시가 초기화됩니다.
        System.out.println("== find all ==");
    }


    @Test
    @Transactional
    @DisplayName("Lazy type은 Course 검색 후 필드 검색을 할 때 N+1 문제가 발생한다.")
    void courseFindLazyTest() {
        System.out.println("== start ==");
        List<Course> courses = courseRepository.findAll();
        System.out.println("== find all ==");

        for (Course course : courses) {
            System.out.println(course.getStudents().size()); // 지연 로딩으로 인해 N+1 문제 발생
        }
    }


    @Test
    @Transactional
    @DisplayName("[N+1]Lazy type은 Course 검색 후 필드 검색을 할 때 N+1 문제가 발생한다.")
    public void testFindAllWithStudents() {
        List<Course> courses = courseRepository.findAllWithStudents();
        for (Course course : courses) {
            System.out.println(course.getName() + ": " + course.getStudents().size() + " students");
        }
    }

    @Test
    @DisplayName("EntityGraph를 사용하여 N+1 문제를 해결한다.")
    void entityGraphTest() {
        System.out.println("== start ==");
        List<Course> courses = courseRepository.findAllWithStudents2();
        System.out.println("== end ==");
        for (Course course : courses) {
            System.out.println(course.getName() + ": " + course.getStudents().size());
        }
    }
    //Pagination 사용해 경고 발생
    @Test
    @DisplayName("fetch join을 paging 처리 사용해 경고 발생.")
    void pagingFetchJoinTest() {
        System.out.println("== start ==");
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Course> courses = courseRepository.findAllWithPages(pageRequest);
        System.out.println("== find all ==");
        for (Course course : courses) {
            System.out.println(course.getName() + ": " + course.getStudents().size());
        }
    }

    @Test
    @Transactional
    @DisplayName("BatchSize,SubSelect를 사용하여 페이징 처리와 N+1 문제 해결하기")
    void pagingBatchSizeTest() {
        System.out.println("== start ==");
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Course> courses = courseRepository.finPagesWithBatch(pageRequest);
        System.out.println("== find all ==");
        for (Course course : courses) {
            System.out.println(course.getName() + ": " + course.getStudents().size());
        }
    }



    @Test
    @DisplayName("MultipleBagFetchException ")
    void multipleBagFetchExceptionTest() {
        List<Course> courses = courseRepository.findAllWithStudentsAndMaterials();
        for (Course course : courses) {
            System.out.println(course.getName() + ": " + course.getStudents().size() + " students, " + course.getMaterials().size() + " materials");
        }
    }

    @Test
    @DisplayName("BatchSize를 사용하여 MultipleBagFetchException N+1 문제 해결하기")
    void batchSizeTest() {
        List<Course> courses = courseService.findAllCourses();
        for (Course course : courses) {
            System.out.println(course.getName() + ": " + course.getStudents().size() + " students, " + course.getMaterials().size() + " materials");
        }
    }

}

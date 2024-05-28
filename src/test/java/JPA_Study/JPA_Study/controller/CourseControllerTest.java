package JPA_Study.JPA_Study.controller;

import JPA_Study.JPA_Study.Repository.CourseRepository;
import JPA_Study.JPA_Study.Repository.StudentRepository;
import JPA_Study.JPA_Study.Service.CourseService;
import JPA_Study.JPA_Study.entity.Course;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("Eager Type은 Us를 단일 조회할 join 문이 날아간다")
    void usingSingleFindTest() {
        System.out.println("== start ==");
        Course course = courseRepository.findById(1L)
                .orElseThrow(RuntimeException::new);
        System.out.println("== end ==");
        System.out.println(course.getName());
    }

    @Test
    public void testPrintCoursesWithStudents() throws Exception {
        // Mock the service method
        doNothing().when(courseService).printAllCoursesWithStudents();

        // Perform the GET request
        mockMvc.perform(get("/courses/print"))
                .andExpect(status().isOk());

        // Verify the service method was called
        verify(courseService).printAllCoursesWithStudents();
    }
}

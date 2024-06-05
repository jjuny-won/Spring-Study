package JPA_Study.JPA_Study.Repository;

import JPA_Study.JPA_Study.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    // JPQL 로 JPA N+1 문제 발생시켜보기
//    @Query("select distinct c from Course c left join c.students")
//    List<Course> findAllWithStudents();

    //Fetch join 을 이용한 N+1 문제 해결
    @Query("select distinct c from Course c left join fetch c.students")
    List<Course> findAllWithStudents();


    //EntitGraph 사용한 fetch join
    @EntityGraph(attributePaths = {"students"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select c from Course c")
    List<Course> findAllWithStudents2();


    //Pagination
    @EntityGraph(attributePaths = {"students"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select c from Course c")
    Page<Course> findAllWithPages(PageRequest pageable);

    //Pagination - Solution
    @Query("select c from Course c")
    Page<Course> finPagesWithBatch(PageRequest pageable);

    @Query("select c from Course c left join fetch c.students left join fetch c.materials")
    List<Course> findAllWithStudentsAndMaterials();

}
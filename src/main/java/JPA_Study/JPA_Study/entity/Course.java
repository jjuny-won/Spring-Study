package JPA_Study.JPA_Study.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 일대다 관계 정의: Course는 여러 Student를 가질 수 있음
    @OneToMany(mappedBy = "course",fetch=FetchType.EAGER) //Lazy
    private Set<Student> students = new HashSet<>();
}

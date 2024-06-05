package JPA_Study.JPA_Study.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
//    @OneToMany(mappedBy = "course",fetch=FetchType.EAGER) //Lazy
//    @BatchSize(size = 10)

//    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY) //Lazy
//    @Fetch(FetchMode.SUBSELECT)
//    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Material> materials = new ArrayList<>();

//    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY) //Lazy
//    @Fetch(FetchMode.SUBSELECT)
//    private Set<Material> materials = new HashSet<>();


}

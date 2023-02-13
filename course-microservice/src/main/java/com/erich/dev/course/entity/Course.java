package com.erich.dev.course.entity;

import com.erich.dev.course.entity.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers = new ArrayList<>() ;

    @Transient
    private List<User> users = new ArrayList<>();

    public void addCourseUser(CourseUser courseUser){
        this.courseUsers.add(courseUser);
    }
    public void deleteCourseUser(CourseUser courseUser){
        this.courseUsers.remove(courseUser);
    }
}

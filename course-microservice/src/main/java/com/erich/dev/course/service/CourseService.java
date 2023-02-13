package com.erich.dev.course.service;

import com.erich.dev.course.dto.CourseDto;
import com.erich.dev.course.entity.Course;
import com.erich.dev.course.entity.models.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();

    Course save(Course course);

    Course update(Course course,Long id);

    Course findById(Long id);

    void deleteById(Long id);

//    ===================================
    //Asiganmos usuario al curso
    User assignUser(User user,Long idCourse);
    //creaamos un usuario
    User createUser(User user,Long idCourse);
    //eliminamos el usuario del curso
    User deleteUser(User user,Long idCourse);
    // listamos los usuarios por id
    Optional<Course> findAllIdByUsers(Long usersIds);

    void deleteCourseUserByUserId(Long id);
//    ====================================
}

package com.erich.dev.course.repository;

import com.erich.dev.course.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepo extends CrudRepository<Course,Long> {

    @Modifying
    @Query("DELETE FROM  CourseUser cu WHERE cu.userId=?1")
    void deleteCourseUserByUserId(Long id);
}

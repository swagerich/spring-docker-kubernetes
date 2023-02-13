package com.erich.dev.course.controller;


import com.erich.dev.course.dto.CourseDto;
import com.erich.dev.course.entity.Course;
import com.erich.dev.course.entity.models.User;
import com.erich.dev.course.service.impl.CourseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    private final CourseServiceImpl courseService;

    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> findAll() {
        return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> save(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(courseService.save(course), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Course>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.findAllIdByUsers(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> update(@Valid @RequestBody Course course, @PathVariable Long id) {
        return new ResponseEntity<>(courseService.update(course, id), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        courseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/assign/{idCourse}")
    public ResponseEntity<?> assignUserToCourse(@RequestBody User user,@PathVariable  Long idCourse){
        return new ResponseEntity<>(courseService.assignUser(user,idCourse),HttpStatus.CREATED);
    }

    @PostMapping("/create-user/{idCourse}")
    public ResponseEntity<?> createUserToCourse(@RequestBody User user,@PathVariable  Long idCourse){
        return new ResponseEntity<>(courseService.createUser(user,idCourse),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{idCourse}")
    public ResponseEntity<?> deleteUserToCourse(@RequestBody User user,@PathVariable  Long idCourse){
        return new ResponseEntity<>(courseService.deleteUser(user,idCourse),HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{idUser}")
    public ResponseEntity<?> deleteUser(@PathVariable  Long idUser){
        courseService.deleteCourseUserByUserId(idUser);
        return ResponseEntity.noContent().build();
    }



}

package com.erich.dev.course.service.impl;


import com.erich.dev.course.clients.UserFeignClient;
import com.erich.dev.course.dto.CourseDto;
import com.erich.dev.course.entity.Course;
import com.erich.dev.course.entity.CourseUser;
import com.erich.dev.course.entity.models.User;
import com.erich.dev.course.exception.NotFoundException;
import com.erich.dev.course.exception.ServerErrorException;
import com.erich.dev.course.repository.CourseRepo;
import com.erich.dev.course.service.CourseService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;

    private final UserFeignClient userFeignClient;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return Streamable.of(courseRepo.findAll())
                .stream()
                .toList();
    }

    @Override
    @Transactional
    public Course save(Course course) {
      /*  Course cour = CourseDto.toEntity(course);
        if (cour != null) {
            return CourseDto.fromEntity(courseRepo.save(cour));
        }*/
        return courseRepo.save(course);
    }

    @Override
    @Transactional
    public Course update(Course course, Long id) {
        if (!courseRepo.existsById(id)) {
            throw new NotFoundException("No existe el id");
        }
   /*     CourseDto byId = this.findById(id);
        byId.setName(course.getName());
        Course cor = CourseDto.toEntity(byId);
        if (cor != null) {
            return CourseDto.fromEntity(courseRepo.save(cor));
        }*/
        Course courId = courseRepo.findById(id).orElseThrow(() -> new NotFoundException("id No encontrado"));
        courId.setName(course.getName());
        return courseRepo.save(courId);
    }

    @Override
    public Course findById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("id No encontrado"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            log.error("id vino null");
            return;
        }
        courseRepo.deleteById(id);
    }


//    =========================================================

    @Override
    @Transactional
    public User assignUser(User user, Long idCourse) {
        Course course = courseRepo.findById(idCourse).orElseThrow(() -> new NotFoundException("id course no encontrado"));
        try {
            User userMicro = userFeignClient.findById(user.getId());
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMicro.getId());
            course.addCourseUser(courseUser);
            courseRepo.save(course);
            return userMicro;
        } catch (FeignException e) {
            throw new ServerErrorException("No existe el usuario (Feign)  /o Ocurrio un problema con la comunicacion");
        }
    }

    @Override
    @Transactional
    public User createUser(User user, Long idCourse) {
        Course course = courseRepo.findById(idCourse).orElseThrow(() -> new NotFoundException("id course no encontrado"));
        try {
            User userNewMicro = userFeignClient.save(user);
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userNewMicro.getId());
            course.addCourseUser(courseUser);
            courseRepo.save(course);
            return userNewMicro;
        } catch (FeignException e) {
            throw new ServerErrorException("No se pudo crear el usuario (Feign)  /o Ocurrio un problema con la comunicacion");
        }
    }

    @Override
    @Transactional
    public User deleteUser(User user, Long idCourse) {
        Course courseId = courseRepo.findById(idCourse).orElseThrow(() -> new NotFoundException("id course no encontrado"));
        try {
            User userMicro = userFeignClient.findById(user.getId());
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMicro.getId());
            courseId.deleteCourseUser(courseUser);
            courseRepo.save(courseId);
            return userMicro;
        } catch (FeignException e) {
            throw new ServerErrorException("No se pudo eliminar el usuario (Feign)  /o Ocurrio un problema con la comunicacion");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findAllIdByUsers(Long usersIds) {
        Course courseId = courseRepo.findById(usersIds).orElseThrow(() -> new NotFoundException("id course encontrado"));
        if (!courseId.getCourseUsers().isEmpty()) {
            List<Long> idUsers = courseId.getCourseUsers()
                    .stream()
                    .map(CourseUser::getUserId).toList();
            List<User> userMicro = userFeignClient.findAllUsersByIdByCourse(idUsers);
            courseId.setUsers(userMicro);
            return Optional.of(courseId);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCourseUserByUserId(Long id) {
        courseRepo.deleteCourseUserByUserId(id);
    }

    //    ===============================================================
}

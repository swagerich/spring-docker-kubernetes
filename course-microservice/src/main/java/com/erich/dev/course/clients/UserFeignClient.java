package com.erich.dev.course.clients;

import com.erich.dev.course.entity.models.User;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "users-msvc", url = "localhost:8001/api/v1/users")
public interface UserFeignClient {

    @GetMapping("{id}")
    User findById(@PathVariable(value = "id") Long id);

    @PostMapping
    User save(@RequestBody User user);

    @GetMapping("/users-by-course")
    List<User> findAllUsersByIdByCourse(@RequestParam List<Long> usersIds);
}

package com.erich.micro.services;

import com.erich.micro.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {


    Iterable<User> findAll();

    User save(User user);

    User update(User user ,Long id);
    User findById(Long id);

    void deleteById(Long id);

//    =========================
    List<User> getAllUsersById(List<Long> idUsers);

//    =========================

}

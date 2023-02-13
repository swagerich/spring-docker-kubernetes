package com.erich.micro.services.impl;

import com.erich.micro.clients.CourseFeignClient;
import com.erich.micro.entity.User;
import com.erich.micro.exception.BadRequestException;
import com.erich.micro.exception.NotFoundException;
import com.erich.micro.repository.UserRepo;
import com.erich.micro.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final CourseFeignClient courseFeignClient;

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("Id no encontrado"));

    }

    @Override
    @Transactional
    public User save(User user) {
        if (!user.getEmail().isEmpty() && userRepo.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email ya existe");
        }
        return userRepo.save(user);
    }

    @Override
    public User update(User user, Long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException("User " + id + " no encontrado");
        }
        return userRepo.findById(id).map(u -> {
            if (!user.getEmail().isEmpty() && !user.getEmail().equalsIgnoreCase(u.getEmail()) && userRepo.existsByEmail(user.getEmail())) {
                throw new BadRequestException("Email ya existe");
            }
            u.setName(user.getName());
            u.setLastName(user.getLastName());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            return userRepo.save(u);
        }).orElseThrow(() -> new BadRequestException("No se pudo actualizar"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            log.error("ID VINO NULL");
            return;
        }
        userRepo.deleteById(id);
        courseFeignClient.deleteUserToCourse(id);
    }

//    ===============================================

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsersById(List<Long> idUsers) {
        return Streamable.of(userRepo.findAllById(idUsers))
                .stream()
                .toList();


//    ===============================================
    }
}

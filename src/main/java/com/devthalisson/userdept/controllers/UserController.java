package com.devthalisson.userdept.controllers;

import com.devthalisson.userdept.entities.User;
import com.devthalisson.userdept.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> findAll() {
        List<User> result = repository.findAll();
        return result;
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable Long id) {
        User user = repository.findById(id).get();
        return user;
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user) {
        User result = repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(value = "/{id}")
    @Transactional
    public User update(@PathVariable Long id, @RequestBody User user) {
        User entity = repository.findById(id).get();
        copyUserToEntity(user, entity);
        entity = repository.save(entity);
        return entity;
    }


    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private void copyUserToEntity(User user, User entity) {
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setDepartment(user.getDepartment());
    }
}

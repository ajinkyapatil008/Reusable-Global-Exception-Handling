package com.company.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.common.exception.exception.ConflictException;
import com.company.common.exception.exception.ForbiddenAccessException;
import com.company.common.exception.exception.InvalidRequestException;
import com.company.common.exception.exception.ResourceNotFoundException;
import com.company.demo.dto.UserRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //GET Mapping

    @GetMapping("/all")
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok("Returning all users");
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable int id){
        if(id == 999)
            throw new ResourceNotFoundException("User 999 not found");

        if(id == 0)
            throw new InvalidRequestException("Invalid ID");

        if (id <= 0)
            throw new InvalidRequestException("ID must be positive");

        return ResponseEntity.ok("User " + id);
    }

    //POST Mapping

    @PostMapping("/createuser")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest request) {

        if (request.getName().equalsIgnoreCase("error")) {
            throw new InvalidRequestException("Name cannot be 'error'");
        }

        return ResponseEntity.ok("User created: " + request.getName());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest request) {

        if (request.getAge() < 18){
            throw new InvalidRequestException("User must be 18+ to register");
        }

        if (request.getName().equalsIgnoreCase("ajinkya")) {
            throw new ConflictException("User already exists with name: " + request.getName());
        }

        return ResponseEntity.ok("User registered: " + request.getName());
    }


    //PUT Mapping

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable int id,
            @Valid @RequestBody UserRequest request) {

        if (id == 999)
            throw new ResourceNotFoundException("Cannot update non-existing user");

        return ResponseEntity.ok("Updated user " + id + " with name " + request.getName());
    }


    //PATCH Mapping

    @PatchMapping("/{id}/name")   //update only name
    public ResponseEntity<String> updateName(
            @PathVariable int id,
            @RequestBody Map<String, String> body) {

        String newName = body.get("name");

        if (newName == null || newName.isBlank())
            throw new InvalidRequestException("Name cannot be empty");

        return ResponseEntity.ok("Updated name of user " + id + " to " + newName);
    }

    @PatchMapping("/{id}/age")    //update only age
    public ResponseEntity<String> updateAge(
            @PathVariable int id,
            @RequestBody Map<String, Integer> body) {

        Integer age = body.get("age");

        if (age == null || age < 1)
            throw new InvalidRequestException("Age must be positive");

        return ResponseEntity.ok("Updated age of user " + id + " to " + age);
    }


    //DELETE Mapping

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {

        if (id == 999)
            throw new ResourceNotFoundException("User " + id + " does not exist");

        return ResponseEntity.ok("Deleted user " + id);
    }


    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllUsers() {

        throw new ForbiddenAccessException("Bulk delete is not allowed");
        //as this is a dangerous activity it will throw a forbidden access exception
        
    }

}

package com.company.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.company.common.exception.exception.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable int id){
        if(id == 999)
            throw new ResourceNotFoundException("User 999 not found");

        if(id == 0)
            throw new InvalidRequestException("Invalid ID");

        return ResponseEntity.ok("User " + id);
    }
}

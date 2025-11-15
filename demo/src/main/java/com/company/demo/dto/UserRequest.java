package com.company.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank(message = "name is required")
    private String name;

    @Min(value = 1, message = "age must be at least 1")
    private int age;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}

package com.example.Capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "Id is required")
    private String id ;

    @NotEmpty(message = "Name is required")
    @Size(min = 4 , message = "Name must contain 4 characters at least")
    private String name ;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive number")
    private double price ;

    @NotNull(message = "Category id is required")
    private String categoryId ;

}

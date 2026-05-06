package com.example.Capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "Id is required")
    private String id ;

    @NotEmpty(message = "User name is required")
    @Size(min = 6 , message = "Name must contain 6 characters at least")
    private String userName ;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$" ,
             message = "Password must be more than 6 characters and include at least one letter and one digit")
    private String password ;

    @NotNull(message = "Email is required")
    @Email
    private String email ;

    @NotEmpty(message = "Role is required")
    @Pattern(regexp = "^(admin|customer)$", message = "Role must be either 'Admin' or 'customer'")
    private String role;

    @NotNull(message = "Balance is required")
    @Positive(message = "Balance must be positive number")
    private double balance ;
}

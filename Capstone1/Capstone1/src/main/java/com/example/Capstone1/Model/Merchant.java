package com.example.Capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "Id is required")
    private String id ;

    @NotEmpty(message = "Name is required")
    @Size(min = 4 , message = "Name must contain 4 characters at least")
    private String name ;
}

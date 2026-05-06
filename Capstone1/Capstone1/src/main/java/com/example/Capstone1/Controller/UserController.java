package com.example.Capstone1.Controller;

import com.example.Capstone1.Api.ApiResponse;
import com.example.Capstone1.Model.Product;
import com.example.Capstone1.Model.User;
import com.example.Capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService ;

    @GetMapping("/get")
    public ResponseEntity<?> getUser(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully !"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        boolean isDeleted = userService.deleteUser(id);

        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id , @RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = userService.updateUser(id,user);

        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @PostMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId , @PathVariable String productId , @PathVariable String merchantId){
        String result = userService.buyProduct(userId,productId,merchantId);

        if(result.equalsIgnoreCase("Success")){
            return ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(400).body(result);
    }



}

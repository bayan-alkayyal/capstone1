package com.example.Capstone1.Controller;

import com.example.Capstone1.Api.ApiResponse;
import com.example.Capstone1.Model.Category;
import com.example.Capstone1.Model.Product;
import com.example.Capstone1.Service.CategoryService;
import com.example.Capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully !"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCategory(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id , @RequestBody @Valid Category category , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = categoryService.updateCategory(id, category);

        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        boolean isDeleted = categoryService.deleteCategory(id);

        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }


}

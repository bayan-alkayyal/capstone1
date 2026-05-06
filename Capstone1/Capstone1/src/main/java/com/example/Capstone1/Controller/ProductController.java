package com.example.Capstone1.Controller;

import com.example.Capstone1.Api.ApiResponse;
import com.example.Capstone1.Model.Product;
import com.example.Capstone1.Service.CategoryService;
import com.example.Capstone1.Service.MerchantService;
import com.example.Capstone1.Service.MerchantStockService;
import com.example.Capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService ;
    private final CategoryService categoryService;
    private final MerchantStockService merchantStockService;
    private final MerchantService merchantService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isAdded = productService.addProduct(product);

        if(isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully !"));
        }

           return ResponseEntity.status(400).body(new ApiResponse("Category does not exist"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProduct(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id , @RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = productService.updateProduct(id,product);

        if(result == -1){
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
        }
        if(result == -2) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Product update successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        boolean isDeleted = productService.deleteProduct(id);

        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchProductByName(@PathVariable String name){
        Product p = productService.searchProductByName(name);

        if(p == null){
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.status(200).body(p);
    }

    @GetMapping("/full-info/{productId}")
    public ResponseEntity<?> getProductFullInfo(@PathVariable String productId) {

        String result = productService.getProductFullInfo(productId,
                        categoryService.getCategories(),
                        merchantStockService.getMerchantStock(),
                        merchantService.getMerchants()
        );

        if (result.equals("Product not found")) {
            return ResponseEntity.status(400).body(result);
        }

        if (result.equals("Category not found")) {
            return ResponseEntity.status(400).body(result);
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/filter/{categoryId}")
    public ResponseEntity<?> filterByCategory(@PathVariable String categoryId){
        ArrayList<Product> products = productService.filterByCategory(categoryId);

        if(products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No category found"));
        }
        return ResponseEntity.status(200).body(products);
    }

    @GetMapping("/count/{categoryId}")
    public ResponseEntity<?> countTheProductInCategory(@PathVariable String categoryId){
        int result = productService.countTheProductInCategory(categoryId);

        if(result == -1){
            return ResponseEntity.status(400).body(new ApiResponse("No category found"));
        }

        if(result ==0){
            return ResponseEntity.status(200).body(new ApiResponse("No product in this category"));
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/average/{categoryId}")
    public ResponseEntity<?> averagePriceOfProduct(@PathVariable String categoryId){

        String result = productService.averagePriceOfProduct(categoryId);

        if (result.equals("Category not found")) {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }

        if (result.equals("No product in this category")) {
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }

        return ResponseEntity.status(200).body(new ApiResponse(result));
    }

    @PutMapping("/discount/{productId}/{discount}")
    public ResponseEntity<?> applyDiscount(@PathVariable String productId, @PathVariable int discount) {
        double result = productService.applyDiscount(productId, discount);

        if (result == -1) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/cheapest/{categoryId}")
    public ResponseEntity<?> cheapestProduct(@PathVariable String categoryId){
        Product product = productService.cheapestProduct(categoryId);
       // return ResponseEntity.status(200).body(productService.cheapestProduct(categoryId));

        if(product == null){
            return ResponseEntity.status(400).body(new ApiResponse("No category found"));
        }

        return ResponseEntity.status(200).body(product);
    }

    @GetMapping("lower-price/{categoryId}/{price}")
    public ResponseEntity<?> getLowerPrice(@PathVariable String categoryId , @PathVariable double price){
        ArrayList<Product> result = productService.getLowerPrice(categoryId, price);

        if(result.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No category found"));
        }
        return ResponseEntity.status(200).body(result);
    }






}







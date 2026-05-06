package com.example.Capstone1.Controller;

import com.example.Capstone1.Api.ApiResponse;
import com.example.Capstone1.Model.MerchantStock;
import com.example.Capstone1.Model.Product;
import com.example.Capstone1.Service.MerchantService;
import com.example.Capstone1.Service.MerchantStockService;
import com.example.Capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/MerchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isAdded = merchantStockService.addMerchantStock(merchantStock);
        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock added successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant or Product not found"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStock() {
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStock());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("Update failed: merchant, product not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(id);

        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock deleted successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock not found"));
    }

    @PutMapping("/add-stock/{productId}/{merchantId}/{amount}")
    public ResponseEntity<?> addMerchantStock(@PathVariable String productId, @PathVariable String merchantId, @PathVariable int amount) {
        boolean stockAdded = merchantStockService.addMerchantStock(productId, merchantId, amount);

        if (stockAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Stock added successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product or merchant stock not found"));
    }

    @GetMapping("/product-merchants/{productId}")
    public ResponseEntity<?> getMerchantsThatSellProduct(@PathVariable String productId) {

        String result = merchantStockService.getMerchantsThatSellProduct(productId);

        if (result.equals("Product not found")) {
            return ResponseEntity.status(400).body(result);
        }

        if (result.equals("No merchants sell this product")) {
            return ResponseEntity.status(200).body(result);
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/available/{productId}")
    public ResponseEntity<?> isProductAvailable(@PathVariable String productId){

        boolean isAvailable = merchantStockService.isProductAvailable(productId);

        if (isAvailable) {
            return ResponseEntity.status(200).body(new ApiResponse("Product is available"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Product is not available"));
    }






}

package com.example.Capstone1.Controller;

import com.example.Capstone1.Api.ApiResponse;
import com.example.Capstone1.Model.Merchant;
import com.example.Capstone1.Model.Product;
import com.example.Capstone1.Service.MerchantService;
import com.example.Capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
           merchantService.addMerchant(merchant);
           return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully !"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMerchant(){
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id , @RequestBody @Valid Merchant merchant , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = merchantService.updateMerchant(id,merchant);

        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        boolean isDeleted = merchantService.deleteMerchant(id);

        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully !"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }

}

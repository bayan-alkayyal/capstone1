package com.example.Capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "Id is required")
    private String id ;

    @NotNull(message = "Product id is required")
    private String productId ;

    @NotNull(message = "Merchant id is required")
    private String merchantId ;

    @NotNull(message = "Stock is required")
    @Min(value = 11 , message = "Stock must be greater than 10" )
    private Integer stock ;

}

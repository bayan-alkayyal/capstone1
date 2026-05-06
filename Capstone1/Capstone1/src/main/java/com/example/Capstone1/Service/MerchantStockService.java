package com.example.Capstone1.Service;

import com.example.Capstone1.Model.Merchant;
import com.example.Capstone1.Model.MerchantStock;
import com.example.Capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

   private final ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    private final MerchantService merchantService ;
    private final ProductService productService;

    public boolean addMerchantStock(MerchantStock merchantStock){
        boolean foundMerchant = false;
        boolean foundProduct = false;


        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantStock.getMerchantId())) {
                foundMerchant = true;
                break;
            }
        }//check if merchant id exists

        for(Product p : productService.getProducts()){
            if(p.getId().equals(merchantStock.getProductId())){
                foundProduct = true ;
                break;
            }
        }//check if product id exists

        if (foundMerchant && foundProduct) {
            merchantStocks.add(merchantStock);
            return true ;
        }//if both exists -> add merchant stock

        return false ;
    }

    public ArrayList<MerchantStock> getMerchantStock(){
        return merchantStocks;
    }

    public boolean updateMerchantStock(String id , MerchantStock merchantStock){

        boolean foundMerchant = false;

        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantStock.getMerchantId())) {
                foundMerchant = true;
                break;
            }
        }

        boolean foundProduct = false;

        for(Product p : productService.getProducts()){
            if(p.getId().equals(merchantStock.getProductId())){
                foundProduct = true;
                break;
            }
        }

        if (!foundMerchant || !foundProduct) {
            return false;
        }

        for(int i = 0 ; i < merchantStocks.size() ; i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i,merchantStock);
                return true ;
            }
        }
        return false ;
    }

    public boolean deleteMerchantStock(String id){
        for(int i = 0 ; i < merchantStocks.size() ; i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(merchantStocks.get(i));
                return true ;
            }
        }
        return false ;
    }

    public boolean addMerchantStock(String productId , String merchantId , int amount){
        for(int i = 0 ; i < merchantStocks.size() ; i++){

        if(merchantStocks.get(i).getProductId().equals(productId) &&
           merchantStocks.get(i).getMerchantId().equals(merchantId)){

           int currentStock = merchantStocks.get(i).getStock();

           merchantStocks.get(i).setStock(currentStock+amount);
           return true;
        }
            }

        return false ;
    }


    public String getMerchantsThatSellProduct(String productId){
        Product product = null;

        for(Product p : productService.getProducts()){
            if(p.getId().equals(productId)){
                product = p ;
                break;
            }
        }

        if(product == null){
            return "Product not found";
        }

        ArrayList<String> merchantNames = new ArrayList<>();

        for (MerchantStock stock : merchantStocks) {

            if (stock.getProductId().equals(productId)) {        //ابحث عن المنتج في المخزون

                for (Merchant m : merchantService.getMerchants()) {  //ابحث عن التاجر المرتبط بهذا المنتج
                    if (m.getId().equals(stock.getMerchantId())) {
                        merchantNames.add(m.getName());
                    }
                }
            }
        }

        if (merchantNames.isEmpty()) {
            return "No merchants sell this product";
        }
        return "Merchants selling " + product.getName() + ": " + merchantNames;
    }

    //endpoint 9

    public boolean isProductAvailable(String productId){
        for(MerchantStock m : merchantStocks){
            if(m.getProductId().equals(productId)){
                if(m.getStock() > 0){
                    return true ;
                }
            }
        }
        return false ;
    }

    //endpoint 10


    }


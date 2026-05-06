package com.example.Capstone1.Service;

import com.example.Capstone1.Model.Merchant;
import com.example.Capstone1.Model.MerchantStock;
import com.example.Capstone1.Model.Product;
import com.example.Capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    private final ProductService productService ;
    private final MerchantService merchantService ;
    private final MerchantStockService merchantStockService ;

    public ArrayList<User> getUsers(){
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public boolean deleteUser(String id){
        for(int i = 0 ; i< users.size() ; i++){
            if(users.get(i).getId().equals(id)){
                users.remove(users.get(i));
                return true ;
            }
        }
        return false ;
    }

    public boolean updateUser(String id , User user){
        for(int i = 0 ; i< users.size() ; i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false ;
    }

    public String buyProduct(String userId , String productId , String merchantId){

        User userFound = null ;
        for(User user : users){
            if(user.getId().equals(userId)){
                userFound = user ;
                break;
            }
        }

        if(userFound == null){
            return "User not found";
        }

        Product productFound = null ;
        for(Product product : productService.getProducts()){
            if(product.getId().equals(productId)){
                productFound = product ;
                break;
            }
        }

        if(productFound == null){
            return "Product not found";
        }

        Merchant merchantFound = null ;
        for(Merchant merchant : merchantService.getMerchants()){
            if(merchant.getId().equals(merchantId)){
                merchantFound = merchant ;
                break;
            }
        }

        if(merchantFound == null){
            return "Merchant not found" ;
        }

        MerchantStock merchantStockAvailable = null ;
        for(MerchantStock merchantStock : merchantStockService.getMerchantStock()){
            if(merchantStock.getProductId().equals(productId) &&
               merchantStock.getMerchantId().equals(merchantId)){

                if(merchantStock.getStock() > 0){
                    merchantStockAvailable = merchantStock;
                    break;
                }
            }
        }
        if(merchantStockAvailable == null){
            return "No product in the stock";
        }


        if(userFound.getBalance() < productFound.getPrice()){
            return "Balance isn't enough" ;
        }

        if(userFound.getBalance() >= productFound.getPrice()){
            userFound.setBalance(userFound.getBalance() - productFound.getPrice());
            merchantStockAvailable.setStock(merchantStockAvailable.getStock() - 1);
        }

        return "Success" ;
    }






}

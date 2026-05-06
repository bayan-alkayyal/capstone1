package com.example.Capstone1.Service;

import com.example.Capstone1.Model.Merchant;
import com.example.Capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();

    public boolean addMerchant(Merchant merchant){
        return merchants.add(merchant);
    }

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean updateMerchant(String id , Merchant merchant){
        for(int i = 0 ; i < merchants.size() ; i++){
            if(merchants.get(i).getId().equals(id)){
                merchants.set(i,merchant);
                return true ;
            }
        }
        return false ;
    }

    public boolean deleteMerchant(String id){
        for(int i = 0 ; i < merchants.size() ; i++){
            if(merchants.get(i).getId().equals(id)){
                merchants.remove(merchants.get(i));
                return true ;
            }
        }
        return false ;
    }

}

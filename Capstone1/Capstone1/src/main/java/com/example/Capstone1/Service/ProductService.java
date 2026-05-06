package com.example.Capstone1.Service;

import com.example.Capstone1.Model.Category;
import com.example.Capstone1.Model.Merchant;
import com.example.Capstone1.Model.MerchantStock;
import com.example.Capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService ;

    public boolean addProduct(Product product){
        boolean foundCategoryId = false ;

        for(Category c : categoryService.getCategories()){
            if(c.getId().equals(product.getCategoryId())){
                foundCategoryId = true ;
                break;
            }
        }

        if(foundCategoryId) {
            products.add(product);
            return true;
        }

        return false;

    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public int updateProduct(String id , Product product){

        boolean foundCategory = false;

        for(Category c : categoryService.getCategories()){
            if(c.getId().equals(product.getCategoryId())){
                foundCategory = true;
                break;
            }
        }

        if(!foundCategory){
            return -1; // category not found
        }

        for(int i = 0 ; i < products.size() ; i++){
            if(products.get(i).getId().equals(id)){
                products.set(i,product);
                return 1 ; //success
            }
        }
        return -2 ; //product not found
    }

    public boolean deleteProduct(String id){
        for(int i = 0 ; i < products.size() ; i++){
            if(products.get(i).getId().equals(id)){
                products.remove(products.get(i));
                return true ;
            }
        }
        return false ;
    }


    public Product searchProductByName(String name) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(name)) {
                return products.get(i);
            }
        }
        return null;
    }//endpoint 1

    public String getProductFullInfo(String productId , ArrayList<Category> categories ,
                                     ArrayList<MerchantStock> merchantStocks , ArrayList<Merchant> merchants){

        Product product = null ;
        for(Product p : products){
            if(p.getId().equals(productId)){
                product = p ;
                break;
            }
        }

        if(product == null){
            return "Product not found";
        }

        Category category = null ;
        for(Category c : categoryService.getCategories()){
            if(c.getId().equals(product.getCategoryId())){
                category = c ;
                break;
            }
        }

        if(category == null){
            return "Category not found";
        }

        ArrayList<String> merchantNames = new ArrayList<>();

        for(MerchantStock merchantStock : merchantStocks) {
            if(merchantStock.getProductId().equals(productId)) {    //اذا المخزون لنفس المنتج

                for(Merchant m : merchants) {
                    if(m.getId().equals(merchantStock.getMerchantId())) {  //
                        merchantNames.add(m.getName());
                    }
                }
            }
        }

        return "Product: " + product.getName() + "\n"
                + "Price: " + product.getPrice() + "\n"
                + "Category: " + category.getName() + "\n"
                + "merchant names: " + merchantNames;

    }
    //endpoint 2

    public ArrayList<Product> filterByCategory(String categoryId){
        ArrayList<Product> result = new ArrayList<>();

        for(Product product : products){
            if(product.getCategoryId().equals(categoryId)){
                result.add(product);
            }
        }
        return result ;

    }//endpoint 3

    public int countTheProductInCategory(String categoryId){
        boolean isCategoryExist = false ;

        for(Category category : categoryService.getCategories()){
            if(category.getId().equals(categoryId)) {
                isCategoryExist = true;
                break;
            }
        }

        if(!isCategoryExist){
            return -1 ; //Category doesn't exist
        }

        int counter = 0 ;

        for(Product product : products){
            if(product.getCategoryId().equals(categoryId)){
                counter++ ;
            }
        }

        if(counter == 0){
         return 0 ;//No product in category
        }

        return counter ;

    }//endpoint 4

    public String averagePriceOfProduct(String categoryId){
        boolean isCategoryExist = false ;

        for(Category category : categoryService.getCategories()){
            if(category.getId().equals(categoryId)){
                isCategoryExist = true ;
                break;
            }
        }

        if(!isCategoryExist){
            return "Category not found";
        }


        double sum = 0;
        int count = 0 ;

        for(Product product : products){
            if(product.getCategoryId().equals(categoryId)){
                count++ ;
                sum = sum + product.getPrice();
            }
        }

        if(count == 0){
            return "No product in this category";
        }

        return "average price of product : " + (sum / count) ;

    }//endpoint 5

    public double applyDiscount(String productId , int discount){
        Product product = null ;
        for(Product p : products){
            if(p.getId().equals(productId)){
                product = p ;
                break;
            }
        }//get the product to apply discount

        if(product== null){
            return -1 ;
        }

        double basePrice = product.getPrice();
        double finalPrice = basePrice - (basePrice * discount / 100);
        product.setPrice(finalPrice);

        return finalPrice;

    }//endpoint 6

    public Product cheapestProduct(String categoryId){
        Product cheapest = null ;

        for(int i = 0 ; i < products.size() ; i++){
            if(products.get(i).getCategoryId().equals(categoryId)){

                cheapest = products.get(i);

                if(products.get(i).getPrice() < cheapest.getPrice()){
                    cheapest = products.get(i);
                }
            }
        }

        return cheapest ;

    }//endpoint 7

    public ArrayList<Product> getLowerPrice(String categoryId , double price){
        ArrayList<Product> result = new ArrayList<>() ;

        for(Product product : products){
            if(product.getCategoryId().equals(categoryId)){
                if(product.getPrice() <= price){
                    result.add(product);
                }
            }
        }
        return result ;

    }//endpoint 8








}//productService class

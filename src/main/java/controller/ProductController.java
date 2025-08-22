package controller;
import java.util.ArrayList;
import mediator.LogMediator;
import model.ProductModel;
import model.UserModel;
import model.UserSellerModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Diogo Lima
 */
@Controller
@RequestMapping ("/")
public class ProductController {
    
        private final LogMediator service;
        private ArrayList <ProductModel> allProducts;

        
        public ProductController (LogMediator service) {
            this.service = service;
        }
        
        //Pag principal de produtos todos os produtos do site
         @GetMapping
           public ArrayList<ProductModel> getAllProducts(ArrayList<UserSellerModel> sellers){
               for (int i=0; i < sellers.size(); i++){
               allProducts.addAll(sellers.get(i).getProductList());
               }
            return  allProducts;
           }
            
         //Pega um produto especifico do servidor;
           @GetMapping ("/product/{name}")
           public ProductModel getProductByName(@PathVariable String name){
            return service.findProductByName(name); 
           }
           
           //cria um produto novo no servidor para vendedores apenas
           @PostMapping ("/log/product/{user}")
           public String createProduct(@RequestBody ProductModel product,@PathVariable UserModel user) {
               if (user.getClass() == UserSellerModel.class){
               try{
               service.logProduct(product.getName(), product.getPrice(), user, product.getDescription(), product.getStock());
               } catch (IllegalArgumentException |  NullPointerException e){
                   System.out.println (e.getMessage());
               }
               }
            return "redirect:/product/{user}";
           }
}

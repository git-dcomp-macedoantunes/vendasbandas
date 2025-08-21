package controller;
import java.util.ArrayList;
import mediator.LogMediator;
import model.ProductModel;
import model.UserSellerModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping ("/product")
public class ProductController {
    
        private final LogMediator service;
        private UserSellerModel vendedor;

        
        public ProductController (LogMediator action, UserSellerModel vendedor) {
            this.service = action;
            this.vendedor = vendedor;
            
        }
        //Pega todos os produtos do servidor;
           @GetMapping
           public ArrayList<ProductModel> getProducts(){
               return service.getProductList (vendedor); 
           }
           
         //Pega um produto especifico do servidor;
           @GetMapping 
           public ProductModel getProductByName(String productName){
               return service.findProductByName(productName); 
           }
           
           //Deleta um produto especifico do servidor.
           @PutMapping
           public boolean deleteProduct (String productName){
               return service.getProductList(vendedor).remove(service.findProductByName(productName));
           }
           
           //cria um produto novo no servidor
           @PostMapping
           public void createProduct(@RequestBody ProductModel product) {
               service.logProduct(product.getName(), product.getPrice(), vendedor, product.getDescription(), product.getStock());
           }

           //Ve qual seller foi escolhido
           public UserSellerModel getSeller (){
               return this.vendedor;
           }
           
           //Coloca o seller escolhido
           @PutMapping
           public void setSeller (UserSellerModel vendedor){
               this.vendedor = vendedor;
           }
           }

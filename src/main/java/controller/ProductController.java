package controller;
import java.util.ArrayList;
import mediator.LogMediator;
import model.ProductModel;
import model.UserSellerModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        
        public ProductController (LogMediator service) {
            this.service = service;
        }
        
        //Pag principal de produtos todos os produtos do site
         @GetMapping
           public ArrayList<ProductModel> getAllProducts(ArrayList<UserSellerModel> sellers){
            return service.getDataService().getProducts();
           }
            
         //Pega um produto especifico do servidor;
           @GetMapping ("/product/{name}")
           public ProductModel getProductByName(@PathVariable String name){
            return service.findProductByName(name); 
           }
}

package controller;
import model.ProductModel;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Diogo Lima
 */
@Controller
public class ProductController {
    
        private final ProductModel service;
        private String caixaTextNome = "" ;
        
        public ProductController (ProductModel service) {
            try{
            this.service = service;
            }catch (IllegalArgumentException e){
                service.setDescription("");
                System.out.println (e);
            }catch (NullPointerException e){
                System.out.println (e);
            }
            
        }
        
           @GetMapping("/")
           public String index(ProductModel model){
               
               model.addAtribute("caixaTextNome", caixaTextNome);
               
               
           }
           }
    
    
    
    
    
}

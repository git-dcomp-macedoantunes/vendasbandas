package controller;

import java.util.ArrayList;
import mediator.LogMediator;
import model.ProductModel;
import model.UserClientModel;
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
@RequestMapping ("/user")
public class UserController {
    
         private final LogMediator service;
         
         public UserController (LogMediator service) {
             this.service = service;
         }
    
        //Olha o carrinho para cliente
        //Olha a lista de produtos a venda para vendedor
        @GetMapping ("/lista/{user}")
        public ArrayList<ProductModel> getProducts(@PathVariable UserModel user){
            return service.getProductList (user);
        }
        
        //Adiciona no carrinho
         @PostMapping ("/lista/{user}/add{product}")
        public String addProductToCarrinho(@PathVariable UserModel user, @PathVariable String product){
            service.addProductToList(user, product);
            return "redirect:/lista/{user}";
        }
        
        //Deleta um produto especifico do servidor se for vendedor
        //Tira do carrinho se for cliente
        @DeleteMapping ("/product/{user}/delete_{name}_da_lista")
        public String deleteProduct (@PathVariable String name, @PathVariable UserModel user){
            service.getProductList(user).remove(service.findProductByName(name));
            return "redirect:/product/{user}";
        }
        
        //Cria um novo usuario;
        @PostMapping ("/log/user")
        public String createUser (@RequestBody UserModel user, String password){
            try{
            if (user.getClass() == UserSellerModel.class){
            service.logUser ("seller", user.getUsername(), password);
            }
            else if (user.getClass() == UserClientModel.class){
            service.logUser ("client", user.getUsername(), password);
            }
        } catch (IllegalArgumentException | NullPointerException e){
                System.out.println (e.getMessage());
             }
            return "redirect:/";
        }
        
        
    
    
}

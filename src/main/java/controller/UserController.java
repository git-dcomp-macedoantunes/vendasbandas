package controller;

import java.io.IOException;
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
         private UserModel userPrincipal;
         
         public UserController (LogMediator service) {
             this.service = service;
             this.userPrincipal = null;
         }
        
        //loga usuario
        @GetMapping ("/login")
        public String logarUsuario (String password, String username){
            
        if (service.getDataService().getUsers().containsKey(username) && service.getDataService().getUsers().get(username).getPassword().equals(password)){
            userPrincipal = service.getDataService().getUsers().get(username);
            return "redirect:/";
        } else {
            IllegalArgumentException e = new IllegalArgumentException("Usuario ou senha incorretos");
             System.out.println(e.getMessage());
             return "/login";
        }
        }
        
        //desloga usuario
        @GetMapping ("/logout")
        public String logoutUsuario() {
            userPrincipal = null;
            return "/login";
        }
         
        //Olha o carrinho para cliente
        //Olha a lista de produtos a venda para vendedor
        @GetMapping ("/lista/{userPrincipal}")
        public ArrayList<ProductModel> getProducts(){
            return service.getProductList (userPrincipal);
        }
        
        //Adiciona no carrinho
         @PostMapping ("/lista/{userPrincipal}/add{product}")
        public String addProductToCarrinho( @PathVariable String product){
             try {
                 service.addProductToList(userPrincipal, product);
             } catch (IOException ex) {
                 System.getLogger(UserController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
             }catch (NullPointerException e){
                System.out.println ("Usuario não está logado");
                return "/login";
        } 
            return "redirect:/lista/{user}";
        }
        
        //Deleta um produto especifico do servidor se for vendedor
        //Tira do carrinho se for cliente
        @DeleteMapping ("/product/{userPrincipal}/delete_{name}_da_lista")
        public String deleteProduct (@PathVariable String name){
            try{
            service.getProductList(userPrincipal).remove(service.findProductByName(name));
            }catch (NullPointerException e){
            System.out.println ("Usuario não está logado");
            return "/login";
        } 
            return "redirect:/product/{userPrincipal}";
        }
        
        //deleta o usuario logado
        @DeleteMapping ("/product/deleteUser")
        public String deleteUser (){
        try{
        if (service.getDataService().getUsers().containsValue(userPrincipal)){
            service.getProductList(userPrincipal).clear();
            service.getDataService().getUsers().remove(userPrincipal.getUsername());
        }else{
            IllegalArgumentException e = new IllegalArgumentException("Usuario não encontrado");
             System.out.println(e.getMessage());
        }
        }catch (NullPointerException e){
            System.out.println ("Usuario não está logado");
        }
            return "redirect:/log/userPrincipal";
        }
        
        //cria um produto novo no servidor para vendedores apenas
           @PostMapping ("/create/product/{userPrincipal}")
           public String createProduct(@RequestBody ProductModel product) {
               if (userPrincipal.getClass() == UserSellerModel.class){
               try{
               service.logProduct(product.getName(), product.getPrice(), userPrincipal, product.getDescription(), product.getStock());
               } catch (IllegalArgumentException |  NullPointerException e){
                   System.out.println (e.getMessage());
               }   catch (IOException ex) {
                       System.getLogger(ProductController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                   }
               }
            return "redirect:/product/{user}";
           }
        
        //Cria um novo usuario;
        @PostMapping ("/sign-in/user")
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
             } catch (IOException ex) {
                 System.getLogger(UserController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
             }
            return "redirect:/";
        }
        
        
    
    
}

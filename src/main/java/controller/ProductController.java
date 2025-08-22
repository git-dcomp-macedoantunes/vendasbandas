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
@RequestMapping ("/product")
public class ProductController {
    
        private final LogMediator service;
        private ArrayList <ProductModel> allProducts;

        
        public ProductController (LogMediator action, UserModel user) {
            this.service = action;
        }
        
        //Pag principal de produtos todos os produtos do site
         @GetMapping ("/product")
           public ArrayList<ProductModel> getAllProducts(ArrayList<UserSellerModel> sellers){
               for (int i=0; i < sellers.size(); i++){
               allProducts.addAll(sellers.get(i).getProductList());
               }
              return  allProducts;
           }
        
        //Olha o carrinho para cliente
        //Olha a lista de produtos a venda para vendedor
           @GetMapping ("/carrinho/{user}")
           public ArrayList<ProductModel> getProducts(@PathVariable UserModel user){
               return service.getProductList (user);
           }
           
           
         //Pega um produto especifico do servidor;
           @GetMapping ("/product/{name}")
           public ProductModel getProductByName(@PathVariable String name){
               return service.findProductByName(name); 
           }
           
           //Deleta um produto especifico do servidor se for vendedor
           //Tira do carrinho se for cliente
           @DeleteMapping ("/product/{user}/delete_{name}_da_lista")
           public String deleteProduct (@PathVariable String name, @PathVariable UserModel user){
                service.getProductList(user).remove(service.findProductByName(name));
               return "redirect:/product/{user}";
           }
           
           //cria um produto novo no servidor para vendedores apenas
           @PostMapping ("/{product}")
           public String createProduct(@RequestBody ProductModel product, @PathVariable UserModel user) {
               if (user.getClass() == UserSellerModel.class){
               service.logProduct(product.getName(), product.getPrice(), user, product.getDescription(), product.getStock());
                }
            return "redirect:/product";
           }
}

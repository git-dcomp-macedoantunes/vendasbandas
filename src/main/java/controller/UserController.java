package controller;

import java.io.IOException;
import java.util.ArrayList;
import mediator.LogMediator;
import model.ProductModel;
import model.UserClientModel;
import model.UserModel;
import model.UserSellerModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final LogMediator service;
    private UserModel userPrincipal;

    public UserController(LogMediator service) {
        this.service = service;
        this.userPrincipal = null;
    }

    @PostMapping("/login")
    public String logarOuCadastrarUsuario(@RequestParam String username,
                                          @RequestParam String password) {
        if (service.getDataService().getUsers().containsKey(username)) {
            // Usuário existe
            if (service.getDataService().getUsers().get(username).getPassword().equals(password)) {
                userPrincipal = service.getDataService().getUsers().get(username);
                return "redirect:/";
            } else {
                System.out.println("Senha incorreta");
                return "login";
            }
        } else {
            // Usuário não existe
            UserSellerModel novoUsuario = new UserSellerModel(username, password);
            service.getDataService().getUsers().put(username, novoUsuario);
            userPrincipal = novoUsuario;
            System.out.println("Usuário criado e logado: " + username);
            return "redirect:/"; // redireciona para home
        }
    }

    // Desloga usuario
    @GetMapping("/logout")
    public String logoutUsuario() {
        userPrincipal = null;
        return "redirect:/login";
    }

    // Olha o carrinho para cliente / lista de produtos para vendedor
    @GetMapping("/lista/{userPrincipal}")
    public ArrayList<ProductModel> getProducts(){
        return service.getProductList(userPrincipal);
    }

    // Adiciona no carrinho
    @PostMapping("/lista/{userPrincipal}/add{product}")
    public String addProductToCarrinho(@PathVariable String product){
        try {
            service.addProductToList(userPrincipal, product);
        } catch (IOException ex) {
            System.getLogger(UserController.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NullPointerException e){
            System.out.println("Usuario não está logado");
            return "/login";
        }
        return "redirect:/lista/{userPrincipal}";
    }

    // Deleta um produto especifico
    @DeleteMapping("/product/{userPrincipal}/delete_{name}_da_lista")
    public String deleteProduct(@PathVariable String name){
        try {
            service.getProductList(userPrincipal).remove(service.findProductByName(name));
        } catch (NullPointerException e){
            System.out.println("Usuario não está logado");
            return "/login";
        }
        return "redirect:/product/{userPrincipal}";
    }

    // Deleta usuario logado
    @DeleteMapping("/product/deleteUser")
    public String deleteUser(){
        try {
            if (service.getDataService().getUsers().containsValue(userPrincipal)){
                service.getProductList(userPrincipal).clear();
                service.getDataService().getUsers().remove(userPrincipal.getUsername());
            } else {
                System.out.println("Usuario não encontrado");
            }
        } catch (NullPointerException e){
            System.out.println("Usuario não está logado");
        }
        return "redirect:/log/userPrincipal";
    }

    // Cria produto
    @PostMapping("/create/product")
    public String createProduct(@RequestParam String name,
                                @RequestParam double price,
                                @RequestParam int stock,
                                @RequestParam String description) {
        if (userPrincipal != null && userPrincipal.getClass() == UserSellerModel.class) {
            try {
                // Cria e adiciona produto
                service.logProduct(name, price, userPrincipal, description, stock);
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException ex) {
                System.out.println("Erro ao salvar produto: " + ex.getMessage());
            }
        }
        return "redirect:/";
    }

    // Cria novo usuario
    @PostMapping("/sign-in/user")
    public String createUser(@RequestBody UserModel user, String password){
        try {
            if (user.getClass() == UserSellerModel.class){
                service.logUser("seller", user.getUsername(), password);
            } else if (user.getClass() == UserClientModel.class){
                service.logUser("client", user.getUsername(), password);
            }
        } catch (IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            System.getLogger(UserController.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return "redirect:/";
    }
}

package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mediator.LogMediator;
import model.ProductModel;
import model.UserClientModel;
import model.UserModel;
import model.UserSellerModel;

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
                                          @RequestParam String password,
                                          @RequestParam String type) {
        if (service.getDataService().getUsers().containsKey(username)) {
            // Usuário existe
            if (service.getDataService().getUsers().get(username).getPassword().equals(password)) {
                userPrincipal = service.getDataService().getUsers().get(username);
                userPrincipal.setMediator(service);
                System.out.println("Usuário logado: " + username);
                service.setUserPrincipal(userPrincipal);
                return "redirect:/";
            } else {
                System.out.println("Senha incorreta");
                return "login";
            }
        } else {
            // Usuário não existe, cria novo usuário do tipo cliente
            // adicionar checkbox para escolher tipo de usuario
            // loga usuario no lugar ai
            try{
            userPrincipal = service.logUser(type, username, password);
            } catch (IllegalArgumentException | IOException e){
                System.out.println(e.getMessage());
            }
            System.out.println("Usuário criado: " + username);
            userPrincipal.setMediator(service);
            service.setUserPrincipal(userPrincipal);
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
    @PostMapping("/addProduct")
    public String addProductToCarrinho(@RequestParam(name = "nomeProduto") String product){
        if (userPrincipal instanceof UserSellerModel){
            System.out.println("Vendedores não podem comprar produtos.");
            return "redirect:/";
        }
        try {
            service.addProductToList(userPrincipal, product);
            service.getDataService().saveToFile();
            System.out.println("Produto adicionado ao carrinho: " + product);
        } catch (IOException ex) {
            System.getLogger(UserController.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NullPointerException e){
            System.out.println("Usuario não está logado");
            return "/login";
        }
        return "redirect:/";
    }

    // Deleta um produto especifico
    @PostMapping("/carrinho/remove")
    public String deleteProduct(@RequestParam(name = "nomeProduto") String product) {
        try {
            // Pega a lista de produtos do usuário
            List<ProductModel> produtos = service.getProductList(userPrincipal);
            System.out.println("Lista antes:" + service.getProductList(userPrincipal));
            boolean removed = false;

            for (int i = 0; i < produtos.size(); i++) {
                if (produtos.get(i).getName().equals(product)) {
                    produtos.remove(i);

                    ProductModel catalogProduct = service.findProductByName(product);
                    if (catalogProduct != null) {
                        catalogProduct.setStock(catalogProduct.getStock() + 1);
                    }

                    removed = true;
                    break;
                }
            }

            if (removed) {
                System.out.println("Produto removido do carrinho: " + product);
                System.out.println("Lista depois:" + service.getProductList(userPrincipal));
            } else {
                System.out.println("Produto não encontrado na lista do usuário: " + product);
            }

            // Salva alterações no arquivo
            service.getDataService().saveToFile();

        } catch (NullPointerException e) {
            System.out.println("Usuário não está logado");
            return "/login";
        } catch (IOException ex) {
            System.getLogger(UserController.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex); }

        return "redirect:/carrinho";
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
                service.getDataService().saveToFile();
                System.out.println(userPrincipal.getUsername());
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
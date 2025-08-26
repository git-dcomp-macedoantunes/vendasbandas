package controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mediator.LogMediator;

@Controller
public class PageController {

    // Lista de produtos disponíveis (simples)
    private ArrayList<String> produtos = new ArrayList<>();
    // Carrinho do usuário (simples)
    private ArrayList<String> carrinho = new ArrayList<>();
    private final LogMediator service = new LogMediator();

    // Página inicial mostrando produtos
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("produtos", service.getDataService().getProducts());
        try {
            service.getDataService().readFromFiles();
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivos: " + e.getMessage());
        }
        return "index"; // retorna index.html
    }

    // Página de login
    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // retorna login.html
    }

    // Página do carrinho
    @GetMapping("/carrinho")
    public String carrinho(Model model) {
        model.addAttribute("itens", service.getUserPrincipal().getProductList());
        model.addAttribute("total", service.getTotal(service.getUserPrincipal()));
        return "carrinho"; // retorna carrinho.html
    }

    // Página de cadastro de produto
    @GetMapping("/produto")
    public String produtoForm() {
        return "produto"; // retorna produto.html
    }

}

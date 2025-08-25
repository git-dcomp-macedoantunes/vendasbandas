package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;

@Controller
public class PageController {

    // Lista de produtos disponíveis (simples)
    private ArrayList<String> produtos = new ArrayList<>();
    // Carrinho do usuário (simples)
    private ArrayList<String> carrinho = new ArrayList<>();

    // Página inicial mostrando produtos
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("produtos", produtos);
        return "index"; // retorna index.html
    }

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // retorna login.html
    }

    // Página do carrinho
    @GetMapping("/carrinho")
    public String carrinho(Model model) {
        double total = 0; // apenas placeholder
        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        return "carrinho"; // retorna carrinho.html
    }

    // Página de cadastro de produto
    @GetMapping("/produto")
    public String produtoForm() {
        return "produto"; // retorna produto.html
    }

}

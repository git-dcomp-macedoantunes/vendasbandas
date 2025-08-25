package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PageController {

    // Lista de produtos disponíveis
    private List<Product> produtos = new ArrayList<>();
    // Carrinho do usuário (simples)
    private List<Product> carrinho = new ArrayList<>();

    // Página inicial mostrando produtos
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("produtos", produtos);
        return "index";
    }

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Página do carrinho
    @GetMapping("/carrinho")
    public String carrinho(Model model) {
        double total = carrinho.stream().mapToDouble(Product::getPrice).sum();
        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        return "carrinho";
    }

    // Página de cadastro de produto
    @GetMapping("/produto")
    public String produtoForm() {
        return "produto";
    }

    // POST para cadastrar produto
    @PostMapping("/produto")
    public String cadastrarProduto(@RequestParam String name,
                                   @RequestParam double price,
                                   @RequestParam int stock,
                                   @RequestParam String description) {
        produtos.add(new Product(name, price, stock, description));
        return "redirect:/";
    }

    // POST para adicionar produto ao carrinho
    @PostMapping("/carrinho/add")
    public String addCarrinho(@RequestParam String nomeProduto) {
        produtos.stream()
                .filter(p -> p.getName().equals(nomeProduto))
                .findFirst()
                .ifPresent(carrinho::add);
        return "redirect:/carrinho";
    }

    // POST para remover produto do carrinho
    @PostMapping("/carrinho/remove")
    public String removeCarrinho(@RequestParam String nomeProduto) {
        carrinho.removeIf(p -> p.getName().equals(nomeProduto));
        return "redirect:/carrinho";
    }

    // Classe Product
    public static class Product {
        private String name;
        private double price;
        private int stock;
        private String description;

        public Product(String name, double price, int stock, String description) {
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.description = description;
        }

        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public String getDescription() { return description; }
    }
}

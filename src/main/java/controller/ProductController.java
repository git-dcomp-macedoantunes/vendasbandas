package controller;

import java.util.ArrayList;
import mediator.LogMediator;
import model.ProductModel;
import model.UserSellerModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class ProductController {

    private final LogMediator service;

    public ProductController(LogMediator service) {
        this.service = service;
    }

    // Pag principal de produtos - retorna lista de produtos
    @GetMapping("/products") // em vez de apenas @GetMapping
    @ResponseBody // se for retornar JSON, ou Model se for Thymeleaf
    public ArrayList<ProductModel> getAllProducts() {
        return service.getDataService().getProducts();
    }


    // Pega um produto específico do servidor
    @GetMapping("/product/{name}")
    public ProductModel getProductByName(@PathVariable String name){
        return service.findProductByName(name);
    }

    // Opcional: GET para renderizar template de produto (se necessário)
    @GetMapping("/produto/{name}")
    public String renderProductPage(@PathVariable String name, Model model) {
        ProductModel product = service.findProductByName(name);
        model.addAttribute("product", product);
        return "produto";
    }
}

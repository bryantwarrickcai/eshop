package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/{productId}/edit")
    public String editProductPage(@PathVariable String productId, Model model) {
        Product product = service.getById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/{productId}/edit")
    public String editProductPost(@ModelAttribute Product updatedProduct, @PathVariable String productId) {
        Product existingProduct = service.getById(productId);

        existingProduct.edit(updatedProduct.getProductName(), updatedProduct.getProductQuantity());

        return "redirect:/product/list";
    }

    @GetMapping("/{productId}/delete")
    public String deleteProductPage(@PathVariable String productId, Model model) {
        Product product = service.getById(productId);
        service.delete(product);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        Map<String, Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }
}

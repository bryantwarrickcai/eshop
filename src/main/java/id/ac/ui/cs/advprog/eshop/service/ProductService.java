package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Map;

public interface ProductService {
    Product create(Product product);
    Product getById(String productId);
    Product delete(Product product);
    Map<String, Product> findAll();
}

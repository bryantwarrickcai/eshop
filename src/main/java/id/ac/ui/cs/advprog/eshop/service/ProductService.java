package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public interface ProductService {
    public Product create(Product product);
    public Product getById(String productId);
    public Product delete(Product product);
    public Map<String, Product> findAll();
}

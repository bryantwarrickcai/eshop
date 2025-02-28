package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Map;

public interface ProductServiceRead {
    Product getById(String productId);
    Map<String, Product> findAll();
}

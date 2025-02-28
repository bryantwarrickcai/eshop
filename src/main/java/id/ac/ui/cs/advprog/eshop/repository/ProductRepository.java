package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Iterator;

public interface ProductRepository {
    Product create(Product product);
    Product getById(String productId);
    Product delete(Product product);
    Iterator<Product> findAll();
}

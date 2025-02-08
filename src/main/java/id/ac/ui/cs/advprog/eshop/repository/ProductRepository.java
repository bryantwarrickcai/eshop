package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
    private Map<String, Product> productData = new HashMap<>();

    public Product create(Product product) {
        productData.put(product.getProductId(), product);
        return product;
    }

    public Product getById(String productId) {
        return productData.get(productId);
    }

    public Product delete(Product product) {
        return productData.remove(product.getProductId());
    }

    public Iterator<Product> findAll() {
        return productData.values().iterator();
    }
}

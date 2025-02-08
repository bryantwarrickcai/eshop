package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    public Product getById(String productId) {
        return productRepository.getById(productId);
    }

    @Override
    public Map<String, Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        Map<String, Product> allProduct = new HashMap<>();
        productIterator.forEachRemaining(p -> allProduct.put(p.getProductId(), p));
        return allProduct;
    }
}

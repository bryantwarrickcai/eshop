package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductServiceRead, ProductServiceWrite {
    @Autowired
    private ProductRepository productRepository;

    public void createRepository() {
        productRepository = new ProductRepositoryImpl();
    }

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public Product getById(String productId) {
        return productRepository.getById(productId);
    }

    @Override
    public Product delete(Product product) {
        productRepository.delete(product);
        return product;
    }

    @Override
    public Map<String, Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        Map<String, Product> allProduct = new HashMap<>();
        productIterator.forEachRemaining(p -> allProduct.put(p.getProductId(), p));
        return allProduct;
    }
}

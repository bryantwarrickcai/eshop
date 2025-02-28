package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductServiceWrite {
    Product create(Product product);
    Product delete(Product product);
}

package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {
    @InjectMocks
    ProductServiceImpl productServiceImpl;
    Product product;

    @BeforeEach
    void setUp() {
        productServiceImpl = new ProductServiceImpl();
        productServiceImpl.createRepository();
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateAndFindProduct() {
        productServiceImpl.create(product);

        Map<String, Product> productMap = productServiceImpl.findAll();
        Product savedProduct = productMap.get("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testGetById() {
        productServiceImpl.create(product);

        Product savedProduct = productServiceImpl.getById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testDelete() {
        // Create the product and then delete it from the database
        productServiceImpl.create(product);
        productServiceImpl.delete(product);

        // Test if the product still exists in the database
        Product savedProduct = productServiceImpl.findAll().get("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // If the product does not exist, it will return null.
        assertNull(savedProduct);
    }
}

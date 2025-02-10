package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    /** This test failed because I used a Map instead of a List. */
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        // I changed the tests a bit in this part because I used a map instead of a list.
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        String[] productIds = {product1.getProductId(), product2.getProductId()};

        Product savedProduct = productIterator.next();
        // Test if the next iterated product ID is one of the products
        boolean exists = false;
        for (String productId : productIds) {
            if (productId.equals(savedProduct.getProductId())) {
                exists = true;
                break;
            }
        }
        assertTrue(exists);

        savedProduct = productIterator.next();
        // Test if the next iterated product ID is one of the products
        exists = false;
        for (String productId : productIds) {
            if (productId.equals(savedProduct.getProductId())) {
                exists = true;
                break;
            }
        }
        assertTrue(exists);

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateAndEdit() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Positive scenario
        String newName = "Hello World";
        int newQuantity = 80;

        product.edit(newName, newQuantity);

        assertEquals(newName, product.getProductName());
        assertEquals(newQuantity, product.getProductQuantity());

        // Negative scenario
        try {
            product.edit("Hello", Integer.parseInt("abc")); // Cannot convert string to int
            fail("Expected NumberFormatException to be thrown");
        } catch (NumberFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete(product);
        // Trying to access the product
        Product obtainedProduct = productRepository.getById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        // Trying to access a non-existent map key will not return an error,
        // but rather will return null instead.
        assertEquals(null, obtainedProduct);

        // Negative scenario: Trying to delete a product that does not exist
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("ea24c24d-a1be-40fd-b201-7d95d6e95151");

        // If the product cannot be found, it will return null.
        Product deletedProduct = productRepository.delete(nonExistentProduct);
        assertEquals(null, deletedProduct);
    }
}

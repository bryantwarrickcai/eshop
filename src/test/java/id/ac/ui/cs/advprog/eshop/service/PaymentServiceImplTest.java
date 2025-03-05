package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentServiceImplTest {
    PaymentServiceImpl paymentServiceImpl;
    List<Product> products;
    Order order1;

    @BeforeEach
    void setUp() {
        paymentServiceImpl = new PaymentServiceImpl();
        products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testOrderAndPaymentStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        Object[] firstPair = paymentServiceImpl.getPaymentList().get(0);
        Order order = (Order) firstPair[0];
        Payment payment = (Payment) firstPair[1];

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testOrderAndPaymentStatusFailed() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "HELLO");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        Object[] firstPair = paymentServiceImpl.getPaymentList().get(0);
        Order order = (Order) firstPair[0];
        Payment payment = (Payment) firstPair[1];

        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testOrderAndPaymentSaveAndCreate() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        Object[] firstPair = paymentServiceImpl.getPaymentList().get(0);
        Order order = (Order) firstPair[0];
        Payment payment = (Payment) firstPair[1];

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());

        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "HELLO");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        Payment myPayment = paymentServiceImpl.getAllPayments().get(0);
        paymentServiceImpl.setStatus(myPayment, PaymentStatus.SUCCESS.getValue());

        Object[] firstPair = paymentServiceImpl.getPaymentList().get(0);
        Order order = (Order) firstPair[0];
        Payment payment = (Payment) firstPair[1];

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusFailed() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        Payment myPayment = paymentServiceImpl.getAllPayments().get(0);
        paymentServiceImpl.setStatus(myPayment, PaymentStatus.REJECTED.getValue());

        Object[] firstPair = paymentServiceImpl.getPaymentList().get(0);
        Order order = (Order) firstPair[0];
        Payment payment = (Payment) firstPair[1];

        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testGetAllPaymentsIfEmpty() {
        List<Payment> allPayments = paymentServiceImpl.getAllPayments();
        assertEquals(null, allPayments);
    }

    @Test
    void testGetAllPaymentsIfNotEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        List<Payment> allPayments = paymentServiceImpl.getAllPayments();
        assertEquals(1, allPayments.size());

        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP1234DEF5678");
        paymentServiceImpl.addPayment(order2, PaymentMethod.VOUCHER.getValue(), paymentData2);

        allPayments = paymentServiceImpl.getAllPayments();
        assertEquals(2, allPayments.size());
    }

    @Test
    void testGetPaymentIfNotFound() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        Payment myPayment = paymentServiceImpl.getPayment("Hello World");
        assertEquals(null, myPayment);
    }

    @Test
    void testGetPaymentIfFound() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        paymentServiceImpl.addPayment(order1, PaymentMethod.VOUCHER.getValue(), paymentData);

        String paymentId = paymentServiceImpl.getAllPayments().get(0).getId(); // ID is randomly generated

        Payment myPayment = paymentServiceImpl.getPayment(paymentId);
        assertEquals(paymentId, myPayment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), myPayment.getMethod());
        assertEquals(paymentData, myPayment.getPaymentData());
    }
}

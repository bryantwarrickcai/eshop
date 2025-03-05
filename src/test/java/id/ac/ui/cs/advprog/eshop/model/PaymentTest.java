package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateNewPaymentInvalidMethod() {
        Map<String, String> paymentData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                    "MY_CUSTOM_METHOD", paymentData);
        });
    }

    @Test
    void testCreateNewPaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "VOUCHER", paymentData);

        assertEquals("SUCCESS", payment.status);
        assertEquals("b0df7140-6baf-4e7b-bdba-392a6e64f16e", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreateNewPaymentVoucherNot16Characters() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABCD5678");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentVoucherNotStartingWithEshop() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "HELLO1234ABC5678");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentVoucherNotContaining8Numbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABCD567");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentVoucherWrongMap() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("randomKey", "random value");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliverySuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", "4");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("SUCCESS", payment.status);
        assertEquals("b0df7140-6baf-4e7b-bdba-392a6e64f16e", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryAddressNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "4");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryAddressEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "4");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryDeliveryFeeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", null);

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryDeliveryFeeEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryMapNotTwoEntries() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", "4");
        paymentData.put("myKey", "My value");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryWrongKeys() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("hello", "world");
        paymentData.put("java", "python");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", payment.status);
    }
}

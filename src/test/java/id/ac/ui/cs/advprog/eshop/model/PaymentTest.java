package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
                PaymentMethod.VOUCHER.getValue(), paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.status);
        assertEquals("b0df7140-6baf-4e7b-bdba-392a6e64f16e", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreateNewPaymentVoucherNot16Characters() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABCD5678");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentVoucherNotStartingWithEshop() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "HELLO1234ABC5678");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentVoucherNotContaining8Numbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABCD567");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentVoucherWrongMap() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("randomKey", "random value");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliverySuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", "4");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.status);
        assertEquals("b0df7140-6baf-4e7b-bdba-392a6e64f16e", payment.getId());
        assertEquals(PaymentMethod.CASH_ON_DELIVERY.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryAddressNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "4");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryAddressEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "4");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryDeliveryFeeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", null);

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryDeliveryFeeEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryMapNotTwoEntries() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Universitas Indonesia");
        paymentData.put("deliveryFee", "4");
        paymentData.put("myKey", "My value");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }

    @Test
    void testCreateNewPaymentCashOnDeliveryWrongKeys() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("hello", "world");
        paymentData.put("java", "python");

        Payment payment = new Payment("b0df7140-6baf-4e7b-bdba-392a6e64f16e",
                PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.status);
    }
}

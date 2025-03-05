package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PaymentRepository {
    // The repository uses a list containing two-element arrays:
    // First element: Order
    // Second element: Payment
    private List<Object[]> paymentList = new ArrayList<>();

    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData);

        // Check if order is already inside the list. If so, remove the old pair and add the new one.
        for (int i = 0; i < paymentList.size(); i++) {
            if (paymentList.get(i)[0] == order) {
                paymentList.remove(i);
                paymentList.add(new Object[]{order, payment});
                this.setStatus(payment, payment.getStatus());
                return payment;
            }
        }
        paymentList.add(new Object[]{order, payment});
        this.setStatus(payment, payment.getStatus());
        return payment;
    }

    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < paymentList.size(); i++) {
            if (paymentList.get(i)[1] == payment) {
                payment.setStatusManual(status);

                if (status.equals(PaymentStatus.REJECTED.getValue())) {
                    ((Order) paymentList.get(i)[0]).setStatus(OrderStatus.FAILED.getValue());
                } else if (status.equals(PaymentStatus.SUCCESS.getValue())) {
                    ((Order) paymentList.get(i)[0]).setStatus(OrderStatus.SUCCESS.getValue());
                }
                return payment;
            }
        }
        throw new IllegalArgumentException();
    }

    public Payment getPayment(String paymentId) {
        for (Object[] element : paymentList) {
            if (((Payment) element[1]).getId().equals(paymentId)) {
                return (Payment) element[1];
            }
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        List<Payment> allPayments = new ArrayList<>();

        if (paymentList.isEmpty()) {
            return null;
        }

        for (Object[] element : paymentList) {
            allPayments.add((Payment) element[1]);
        }
        return allPayments;
    }

    public List<Object[]> getPaymentList() {
        return paymentList;
    }
}

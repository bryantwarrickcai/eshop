package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    public Payment(String id, String method, Map<String, String> paymentData) {
        this(id, null, null, paymentData);
        this.setMethod(method);
        this.setStatus();
    }

    public void setMethod(String method) {
        if (PaymentMethod.contains(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setStatus() {
        // Check the type of payment method
        if (this.method.equals(PaymentMethod.VOUCHER.getValue())) {
            if (this.paymentData.size() != 1 || !this.paymentData.containsKey("voucherCode")) {
                this.status = "REJECTED";
            } else {
                String voucherCode = this.paymentData.get("voucherCode");

                int numberOfNumericCharacters = 0;
                for (char c : voucherCode.toCharArray()) {
                    if (Character.isDigit(c)) {
                        numberOfNumericCharacters++;
                    }
                }

                if (voucherCode.length() == 16 &&
                        voucherCode.substring(0, 5).equals("ESHOP") &&
                        numberOfNumericCharacters == 8) {
                    this.status = "SUCCESS";
                } else {
                    this.status = "REJECTED";
                }
            }
        } else if (this.method.equals(PaymentMethod.CASH_ON_DELIVERY.getValue())) {
            if (this.paymentData.size() != 2 ||
                    !this.paymentData.containsKey("address") ||
                    !this.paymentData.containsKey("deliveryFee")) {
                this.status = "REJECTED";
            } else {
                String address = this.paymentData.get("address");
                String deliveryFee = this.paymentData.get("deliveryFee");
                if (address == null || address.isEmpty() || deliveryFee == null || deliveryFee.isEmpty()) {
                    this.status = "REJECTED";
                } else {
                    this.status = "SUCCESS";
                }
            }
        }
    }
}

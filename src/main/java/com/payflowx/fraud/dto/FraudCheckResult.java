package com.payflowx.fraud.dto;

public record FraudCheckResult(boolean approved, String reason) {

    public static FraudCheckResult approve() {
        return new FraudCheckResult(true, null);
    }

    public static FraudCheckResult rejected(String reason) {
        return new FraudCheckResult(false, reason);
    }
}
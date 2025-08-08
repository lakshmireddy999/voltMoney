package com.example.volt.Utilities.enums;

public enum Environment {

    test("voltmoney.in/check-loan-eligibility-against-mutual-funds");

    Environment(String voltUrl) {
        this.voltUrl = "https://" + voltUrl;
    }

    private final String voltUrl;

    public String getVoltUrl() {
        return this.voltUrl;
    }
}

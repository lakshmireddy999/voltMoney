package com.example.volt.Utilities.enums;

import lombok.Getter;

@Getter
public enum Environment {

    test("voltmoney.in/check-loan-eligibility-against-mutual-funds");

    Environment(String voltUrl) {
        this.voltUrl = "https://" + voltUrl;
    }

    private final String voltUrl;
}

package com.autoscout24.domain;

public enum Fuel {
    DIESEL("Diesel"),
    GAS("Gasoline");

    private String name;

    Fuel(String fuel) {
        this.name = fuel;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

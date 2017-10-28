package com.autoscout24.domain;

public enum Fuel {
    DIESEL("Diesel"),
    GAS("Gasoline"),
    ELECTRIC("Electric");
    private String fuelType;

    Fuel(String fuelType) {
        this.fuelType = fuelType;
    }

    public String type(){
        return fuelType;
    }
}

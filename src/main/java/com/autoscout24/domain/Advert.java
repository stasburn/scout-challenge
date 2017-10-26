package com.autoscout24.domain;

import java.time.LocalDate;

public class Advert {
    private final long id;
    private final String title;
    private final Fuel fuel;
    private final int price;
    private final boolean newCar;
    private final int mileage;
    private final LocalDate firstReg;

    public Advert(long id, String title, Fuel fuel, int price, boolean newCar, int mileage, LocalDate firstReg) {
        this.id = id;
        this.title = title;
        this.fuel = fuel;
        this.price = price;
        this.newCar = newCar;
        this.mileage = mileage;
        this.firstReg = firstReg;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public int getPrice() {
        return price;
    }

    public boolean isNewCar() {
        return newCar;
    }

    public int getMileage() {
        return mileage;
    }

    public LocalDate getFirstReg() {
        return firstReg;
    }
}

package com.autoscout24.domain;

import java.time.LocalDate;

public class Advert {
    private final long id;
    private final String title;
    private final Fuel fuel;
    private final int price;
    private final boolean _new;
    private final int mileage;
    private final LocalDate firstReg;

    public Advert(long id, String title, Fuel fuel, int price, boolean _new, int mileage, LocalDate firstReg) {
        this.id = id;
        this.title = title;
        this.fuel = fuel;
        this.price = price;
        this._new = _new;
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

    public boolean is_new() {
        return _new;
    }

    public int getMileage() {
        return mileage;
    }

    public LocalDate getFirstReg() {
        return firstReg;
    }

}

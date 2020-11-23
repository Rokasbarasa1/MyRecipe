package com.example.myrecipe.models;

public class Ingredient {
    private String rawString;
    private String name;
    private double quantity;
    private String unitOfMeassure;

    public Ingredient() {
        rawString = "";
        name = "";
        quantity = 0;
        unitOfMeassure = "";
    }

    public Ingredient(String name, double quantity, String unitOfMeassure) {
        this.rawString = name + " " + quantity;
        this.name = name;
        this.quantity = quantity;
        this.unitOfMeassure = unitOfMeassure;
    }

    public String getRaw() {
        return rawString;
    }

    public void setRaw(String raw) {
        this.rawString = raw;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnitOfMeassure() {
        return unitOfMeassure;
    }

    public String getAsString(){
        return name+" "+quantity+ " " +unitOfMeassure;
    }
}

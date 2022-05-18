package com.schfr.virtual_pet;

public class Food {
    double foodValue, happinessLvl;
    String foodName;

    public void cupcake() {
        foodName = "Cupcake";
        foodValue = -0.1;
        happinessLvl = 0.3;
    }
    public void pizza() {
        foodName = "Pizza";
        foodValue = 0.1;
        happinessLvl = 0.3;
    }
    public void broccoli() {
        foodName = "Broccoli";
        foodValue = 0.4;
        happinessLvl = -0.1;
    }
}

package com.schfr.virtual_pet;

import com.schfr.virtual_pet.view.PetView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class GameController{

    public Food broccoli = new Food(), cupcake = new Food(), pizza = new Food();
    public ImageView broccoliB, cupcakeB, pizzaB;


    @FXML
    private Canvas petCanvas;
    @FXML
    private ProgressBar healthBar, happinessBar;


    Pet pet;
    PetView petView;

    public void start(){

        System.out.println("init");
        Var.init();
        pet = new Pet();
        petView = new PetView(petCanvas);
        petView.setPetCanvas(pet);
        pet.petA(petView, pet);
        pet.startValues();
        foodValue();
    }

    public void update() {

        if (Var.switchScreen == Display.HOME_SCREEN) {
            petView.setPetCanvas(pet);
            petCanvas.setVisible(true);
            showFood(false);
        }
        if (Var.switchScreen == Display.FOOD_SCREEN) {
            petCanvas.setVisible(false);
            showFood(true);
        }
        healthBar.setProgress(pet.healthLvl);
        happinessBar.setProgress(pet.happinessLvl);
        pet.petHealth();
        pet.petHappiness();
    }

    public void giveB() {
        pet.feeding(broccoli);
    }

    public void giveC() {
        pet.feeding(cupcake);
    }

    public void giveP() {
        pet.feeding(pizza);
    }

    public void foodValue(){
        broccoli.broccoli();
        cupcake.cupcake();
        pizza.pizza();
    }
    public void showFood(Boolean b){
        broccoliB.setVisible(b);
        cupcakeB.setVisible(b);
        pizzaB.setVisible(b);
    }

    public void homeScreen() {
        Var.switchScreen = Display.HOME_SCREEN;
    }

    public void foodScreen() {
        Var.switchScreen = Display.FOOD_SCREEN;
    }
}
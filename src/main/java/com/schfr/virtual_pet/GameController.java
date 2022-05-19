package com.schfr.virtual_pet;

import com.schfr.virtual_pet.view.HeartView;
import com.schfr.virtual_pet.view.PetView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class GameController{

    public Food broccoli = new Food(), cupcake = new Food(), pizza = new Food();
    public ImageView broccoliB, cupcakeB, pizzaB;
    public Canvas heartCanvas;
    public ImageView homeB, cutleryB;


    @FXML
    private Canvas petCanvas;
    @FXML
    private ProgressBar healthBar, happinessBar;


    Pet pet;
    PetView petView;
    HeartView heartView;

    public void start(){

        Var.init();
        pet = new Pet();
        petView = new PetView(petCanvas);
        heartView = new HeartView(heartCanvas);
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
            cutleryB.setOpacity(0.4);
            homeB.setOpacity(1);
        }
        if (Var.switchScreen == Display.FOOD_SCREEN) {
            petCanvas.setVisible(false);
            showFood(true);
            homeB.setOpacity(0.4);
            cutleryB.setOpacity(1);
        }
        healthBar.setProgress(pet.healthLvl);
        happinessBar.setProgress(pet.happinessLvl);
        pet.petHealth();
        pet.petHappiness();
        pet.updatePet();
        heartView.setHeartCanvas(pet);
        Var.currentTime = System.currentTimeMillis();
        if (pet.moreLove && !pet.runningLove){
            pet.petLove();
        }
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
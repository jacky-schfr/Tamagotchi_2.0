package com.schfr.virtual_pet;

import com.schfr.virtual_pet.view.HeartView;
import com.schfr.virtual_pet.view.PetView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.json.JSONObject;

public class GameController{

    public Food broccoli = new Food(), cupcake = new Food(), pizza = new Food();
    public ImageView broccoliB, cupcakeB, pizzaB;
    public Canvas heartCanvas;
    public ImageView homeB, cutleryB;
    public ImageView restartB;


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
        pet.petA();
        foodValue();
        if(Objects.equals(pet.name, "")){
            Var.switchScreen = Display.NEW_PET;
            pet.startValues();
        }
        else {
            reloadSave(pet);
        }
    }

    public void update() {

        if (Var.switchScreen == Display.HOME_SCREEN) {
            petView.setPetCanvas(pet);
            petCanvas.setVisible(true);
            showFood(false);
            restartB.setVisible(false);
            cutleryB.setVisible(true);
            homeB.setVisible(true);
            cutleryB.setOpacity(0.4);
            homeB.setOpacity(1);
            if(Var.mood == Mood.DEAD){
                cutleryB.setVisible(false);
                homeB.setVisible(false);
                pet.name = "";
                }
            }

        if (Var.switchScreen == Display.FOOD_SCREEN) {
            petCanvas.setVisible(false);
            showFood(true);
            restartB.setVisible(false);
            homeB.setOpacity(0.4);
            cutleryB.setOpacity(1);
        }
        if (Var.switchScreen == Display.RESTART){
            petCanvas.setVisible(false);
            showFood(false);
            homeB.setVisible(false);
            cutleryB.setVisible(false);
            restartB.setVisible(true);
        }
        healthBar.setProgress(pet.healthLvl);
        happinessBar.setProgress(pet.happinessLvl);
        pet.petHealth();
        pet.petHappiness();
        pet.updatePet();
        checkMood(pet);
        heartView.setHeartCanvas(pet);
        Var.currentTime = System.currentTimeMillis();
        if (pet.moreLove && !pet.runningLove){
            pet.petLove();
        }
        if (Var.currentTime%5 == 0){
            save(pet);
        }
        System.out.println(Var.mood + " - "+Var.switchScreen.toString()) ;
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
    public static void save(Pet p){
        try{
            String content = new String(Files.readAllBytes(Paths.get(Var.petSave.toURI())), "UTF-8");
            JSONObject json = new JSONObject(content);

            json.put("name", p.name);
            json.put("loveLvl", p.loveLvl);
            json.put("health", p.healthLvl);
            json.put("happiness", p.happinessLvl);

            FileWriter fw = new FileWriter(Var.petSave);
            fw.write(json.toString());
            fw.flush();
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void reloadSave(Pet p){
        try{
            String content = Files.readString(Paths.get(Var.petSave.toURI()));
            JSONObject json = new JSONObject(content);

            p.name = json.getString("name");
            p.loveLvl = json.getDouble("loveLvl");
            p.healthLvl = json.getDouble("health");
            p.happinessLvl = json.getDouble("happiness");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void checkMood(Pet p){
        if(p.happinessLvl > 0.3){
            Var.mood = Mood.HAPPY;
        }
        if(p.happinessLvl <= 0.3 && Var.mood != Mood.ILL && Var.mood != Mood.DEAD){
            Var.mood = Mood.SAD;
        }
        if (p.happinessLvl == 0 && p.illTimer == null) {
            p.illness();
        }
    }
    public void restart() throws IOException {
        System.out.println("RESTTTTTTTTT");
        Var.petSave.createNewFile();
        System.out.println(Var.switchScreen);
        Var.switchScreen = Display.HOME_SCREEN;
        System.out.println(Var.switchScreen);
    }
    public void simpleClick() {
        if (Var.mood == Mood.DEAD){
            pet.startValues();
            Var.mood = Mood.HAPPY;
            Var.switchScreen = Display.RESTART;
        }
    }
}
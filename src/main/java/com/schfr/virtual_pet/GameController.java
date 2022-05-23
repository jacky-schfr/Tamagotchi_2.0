package com.schfr.virtual_pet;

import com.schfr.virtual_pet.view.HeartView;
import com.schfr.virtual_pet.view.PetView;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.json.JSONObject;

public class GameController {

    public Food broccoli = new Food(), cupcake = new Food(), pizza = new Food();
    public ImageView broccoliB, cupcakeB, pizzaB;
    public Canvas heartCanvas, petCanvas;
    public ImageView homeB, cutleryB, medicineB;
    public ImageView restartB;
    public TextField inputName;
    public Label nameQuestion;
    public Button startB;
    public ProgressBar healthBar, happinessBar;
    public Button giveMed;


    Pet pet;
    PetView petView;
    HeartView heartView;

    public void start() throws IOException {

        Var.init();
        pet = new Pet();
        petView = new PetView(petCanvas);
        heartView = new HeartView(heartCanvas);
        petView.setPetCanvas(pet);
        pet.petA();
        foodValue();
        if (Objects.equals(pet.name, "")) {
            Var.switchScreen = Display.NEW_PET;
            pet.setValues();
        } else {
            reloadSave(pet);
        }
    }

    public void update() {
        // Drawing the different Scenes
        if (Var.switchScreen == Display.HOME_SCREEN) {
            healthBar.setVisible(true);
            happinessBar.setVisible(true);
            petView.setPetCanvas(pet);
            petCanvas.setVisible(true);
            showFood(false);
            restartB.setVisible(false);
            giveMed.setVisible(false);
            cutleryB.setVisible(true);
            homeB.setVisible(true);
            medicineB.setVisible(true);
            medicineB.setOpacity(0.4);
            cutleryB.setOpacity(0.4);
            homeB.setOpacity(1);
            startB.setVisible(false);
            inputName.setVisible(false);
            nameQuestion.setVisible(false);
            if (Var.mood == Mood.DEAD) {
                cutleryB.setVisible(false);
                homeB.setVisible(false);
            }
        }
        if (Var.switchScreen == Display.MEDICINE_SCREEN){
            healthBar.setVisible(true);
            happinessBar.setVisible(true);
            petView.setPetCanvas(pet);
            petCanvas.setVisible(true);
            showFood(false);
            restartB.setVisible(false);
            giveMed.setVisible(true);
            cutleryB.setVisible(true);
            homeB.setVisible(true);
            medicineB.setVisible(true);
            medicineB.setOpacity(1);
            cutleryB.setOpacity(0.4);
            homeB.setOpacity(0.4);
            startB.setVisible(false);
            inputName.setVisible(false);
            nameQuestion.setVisible(false);
            if (Var.mood == Mood.DEAD) {
                cutleryB.setVisible(false);
                homeB.setVisible(false);
            }
        }
        if (Var.switchScreen == Display.FOOD_SCREEN) {
            healthBar.setVisible(true);
            happinessBar.setVisible(true);
            petCanvas.setVisible(false);
            showFood(true);
            restartB.setVisible(false);
            giveMed.setVisible(false);
            homeB.setOpacity(0.4);
            cutleryB.setOpacity(1);
            medicineB.setOpacity(0.4);
            startB.setVisible(false);
            inputName.setVisible(false);
            nameQuestion.setVisible(false);
        }
        if (Var.switchScreen == Display.RESTART) {
            healthBar.setVisible(false);
            happinessBar.setVisible(false);
            petCanvas.setVisible(false);
            showFood(false);
            homeB.setVisible(false);
            cutleryB.setVisible(false);
            restartB.setVisible(true);
            startB.setVisible(false);
            inputName.setVisible(false);
            nameQuestion.setVisible(false);
        }
        if (Var.switchScreen == Display.NEW_PET) {
            healthBar.setVisible(false);
            happinessBar.setVisible(false);
            petCanvas.setVisible(false);
            showFood(false);
            homeB.setVisible(false);
            cutleryB.setVisible(false);
            restartB.setVisible(false);
            startB.setVisible(true);
            inputName.setVisible(true);
            nameQuestion.setVisible(true);
        }
        // updating the pet
        if (Var.mood != Mood.DEFAULT && Var.mood != Mood.DEAD) {
            healthBar.setProgress(pet.healthLvl);
            happinessBar.setProgress(pet.happinessLvl);
            pet.petHealth();
            pet.petHappiness();
            pet.updatePet();
            setMood(pet);
        }
        // updating the pet love
        heartView.setHeartCanvas(pet);
        Var.currentTime = System.currentTimeMillis();
        if (pet.moreLove && !pet.runningLove) {
            pet.petLove();
        }
        // saving the data
        if (Var.currentTime % 5 == 0) {
            save(pet);
        }
        System.out.println(Var.mood);
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

    public void foodValue() {
        broccoli.broccoli();
        cupcake.cupcake();
        pizza.pizza();
    }

    public void showFood(Boolean b) {
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

    public void medicineScreen() {
        Var.switchScreen = Display.MEDICINE_SCREEN;
    }
    public void giveMeds() {
        if(Var.mood == Mood.ILL){
            if (pet.healthLvl<0.5){
                pet.healthLvl = 0.5;
            }
            if (pet.happinessLvl<0.5){
                pet.happinessLvl = 0.5;
            }
            Var.mood = Mood.HAPPY;
        }
    }

    public static void save(Pet p) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadSave(Pet p) throws IOException {
        String content = Files.readString(Paths.get(Var.petSave.toURI()));
        JSONObject json = new JSONObject(content);

        p.name = json.getString("name");
        p.loveLvl = json.getDouble("loveLvl");
        p.healthLvl = json.getDouble("health");
        p.happinessLvl = json.getDouble("happiness");
    }

    public static void setMood(Pet p) {
        if (p.happinessLvl > 0.3) {
            Var.mood = Mood.HAPPY;
        }
        if (p.happinessLvl <= 0.3 && Var.mood != Mood.ILL && Var.mood != Mood.DEAD) {
            Var.mood = Mood.SAD;
        }
        if (p.happinessLvl == 0 && p.illTimer == null) {
            p.illness();
        }
    }

    public void simpleClick() {
        if (Var.mood == Mood.DEAD) {
            Var.mood = Mood.DEFAULT;
            pet.name = "";
            Var.switchScreen = Display.RESTART;
        }
    }

    public void restart() throws IOException {
        Var.petSave.createNewFile();
        pet.setValues();
        Var.switchScreen = Display.NEW_PET;
    }

    public void newPet() {
        pet.name = inputName.getText();
        Var.mood = Mood.HAPPY;
        Var.switchScreen = Display.HOME_SCREEN;
    }
}
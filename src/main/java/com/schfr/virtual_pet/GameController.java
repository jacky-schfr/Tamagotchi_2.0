package com.schfr.virtual_pet;

import com.schfr.virtual_pet.view.PetView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Canvas petCanvas;

    private PetView petView;

    Pet pet = new Pet();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        petView = new PetView(petCanvas);
    }

    public void start(){
        // starting stats or load safe...
        petView.setCanvas(pet);
        pet.petA(petView, pet);
    }
    public void update(){
        // if a value changes, change it in the update.

    }
}
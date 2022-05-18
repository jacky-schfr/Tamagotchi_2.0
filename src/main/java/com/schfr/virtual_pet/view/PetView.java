package com.schfr.virtual_pet.view;

import com.schfr.virtual_pet.Pet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class PetView {

    private final Canvas canvas;
    public PetView(Canvas view) {
        this.canvas = view;
    }

    public void setPetCanvas(Pet pet) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0 , 0, canvas.getWidth(), canvas.getHeight());
        g.drawImage(new Image(pet.filePet), 0, 0);
    }
}

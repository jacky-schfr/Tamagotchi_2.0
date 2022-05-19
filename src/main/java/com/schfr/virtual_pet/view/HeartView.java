package com.schfr.virtual_pet.view;

import com.schfr.virtual_pet.Heart;
import com.schfr.virtual_pet.Pet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class HeartView {

    private final Canvas canvas;
    public HeartView(Canvas view) {
        this.canvas = view;
    }

    public void setHeartCanvas(Pet pet) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0 , 0, canvas.getWidth(), canvas.getHeight());
        if (pet.loveLvl < 100) {
            g.drawImage(new Image(Heart.risingHeart((int) pet.loveLvl/5)),0,0);
        } else {
            g.drawImage(new Image(Heart.risingHeart(19)),0,0);
        }
    }

}

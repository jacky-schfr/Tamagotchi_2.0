package com.schfr.virtual_pet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameLogic extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameLogic.class.getResource("GameLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);

        GameController controller = fxmlLoader.getController();


        stage.setTitle("Virtual Pet");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        controller.start();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.update();
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch();
        System.exit(0);
    }
}
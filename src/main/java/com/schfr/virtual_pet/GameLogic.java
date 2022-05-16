package com.schfr.virtual_pet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

        Timer timer = new Timer();
        controller.start();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                controller.update();
            }
        }, 0, 60);
    }

    public static void main(String[] args) {
        launch();
        System.exit(0);
    }
}
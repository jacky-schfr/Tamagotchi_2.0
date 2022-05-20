package com.schfr.virtual_pet;

import java.io.File;

public class Var {

    public static File petSave = new File("src/main/java/com/schfr/virtual_pet/save/pet.json");

    public static int animationPetFileInt = 0;

    public static long currentTime;
    public static long animationTimer;
    public static long healthTimer, loveTimer, happinessTimer, hygeneTimer, illnessTimer, deathTimer;
    public static Display switchScreen;
    public static Mood mood;

    public static boolean petC, foodC;

    public static long setTimer() {
        return System.currentTimeMillis();
    }
    public static void init() {
        switchScreen = Display.HOME_SCREEN;
        mood = Mood.HAPPY;
    }
}


enum Display {
    NEW_PET,
    HOME_SCREEN,
    FOOD_SCREEN,
    RESTART
}
enum Mood {
    HAPPY,
    SAD,
    ILL,
    DEAD
}

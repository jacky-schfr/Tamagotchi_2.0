package com.schfr.virtual_pet;

import javafx.animation.AnimationTimer;

public class Pet {
    public double happinessLvl, healthLvl, happinessMax, healthMax, loveLvl;
    String name;
    String[] petFile = {"p1", "p2", "p3"};
    String[] sickPetFile = {"ip1"};
    String[] unhappyPetFile = {"sp1"};
    String[] deadPetFile = {"dp1"};
    public String filePet = "com/schfr/virtual_pet/images/pet/" + petFile[Var.animationPetFileInt] + ".png";
    AnimationTimer lveTimer = null, illTimer = null, deadTimer = null;
    AnimationTimer aPetTimer = null;
    Boolean moreLove, runningLove = false;


    public void petA() {
        final long[] lastUpdate = {0};
        aPetTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate[0] >= 600_000_000) {
                    if (Var.animationPetFileInt < 2) {
                        Var.animationPetFileInt++;
                    } else {
                        Var.animationPetFileInt = 0;
                    }
                    lastUpdate[0] = now;
                    definePet();
                }
            }
        };
        aPetTimer.start();
    }

    public String definePet() {
        if (Var.mood == Mood.HAPPY) {
            filePet = "com/schfr/virtual_pet/images/pet/" + petFile[Var.animationPetFileInt] + ".png";
        }
        if (Var.mood == Mood.SAD) {
            filePet = "com/schfr/virtual_pet/images/pet/" + unhappyPetFile[0] + ".png";
        }
        if (Var.mood == Mood.ILL) {
            filePet = "com/schfr/virtual_pet/images/pet/" + sickPetFile[0] + ".png";
        }
        if (Var.mood == Mood.DEAD) {
            filePet = "com/schfr/virtual_pet/images/pet/" + deadPetFile[0] + ".png";
        }
        if (Var.mood == Mood.DEFAULT) {
            filePet = "com/schfr/virtual_pet/images/pet/" + petFile[0] + ".png";
        }
        return filePet;
    }


    public Pet() {
        this.happinessMax = 1;
        this.healthMax = 1;
    }

    public void setValues() {
        this.happinessLvl = 0.5;
        this.healthLvl = 0.5;
        this.loveLvl = 0;
    }

    public void petHealth() {
        if (healthLvl > 0) {
            healthLvl -= 0.0005;
            if (healthLvl <= 0) {
                healthLvl = 0;
            }
        }
    }

    public void petHappiness() {
        if (happinessLvl != 0.00) {
            happinessLvl -= 0.0005;
            if (happinessLvl <= 0) {
                happinessLvl = 0;
            }
        }
    }

    public void petLove() {
        Var.loveTimer = Var.setTimer();
        lveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (loveLvl < 100) {
                    if (!moreLove) {
                        lveTimer.stop();
                        runningLove = false;
                    }
                    if ((Var.currentTime - Var.loveTimer) >= 3500) {
                        loveLvl += 5;
                        Var.loveTimer = System.currentTimeMillis();
                    }
                }

            }
        };
        lveTimer.start();
        runningLove = true;
    }


    public void updatePet() {
        moreLove = healthLvl >= 0.5 && happinessLvl >= 0.3 || healthLvl >= 0.3 && happinessLvl >= 0.5;

    }

/*        if(healthLvl>30) {
            System.out.println("Your pet" + name + "is healthy.");
        }
        else if(healthLvl>10){
            System.out.println("Please take better care of "+name+"!");
        }
        else {
            System.out.println(name+" is ill. Please give "+name+" some medicine and take better care or "+name);
        }*/

    public void feeding(Food f) {
        if (Var.mood != Mood.ILL && Var.mood != Mood.DEAD){
            happinessLvl = happinessLvl + f.happinessLvl;

            if (happinessLvl > 1) {
                happinessLvl = happinessMax;
            }
            if (happinessLvl < 0) {
                happinessLvl = 0;
            }
            healthLvl = healthLvl + f.foodValue;

            if (healthLvl > 1) {
                healthLvl = happinessMax;
            }
            if (healthLvl < 0) {
                healthLvl = 0;
            }
        }
    }

    public void illness() {
        Var.illnessTimer = Var.setTimer();
        illTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (happinessLvl != 0) {
                    illTimer.stop();
                    illTimer = null;
                }
                if (happinessLvl == 0) {
                    if ((Var.currentTime - Var.illnessTimer) >= 3500) {
                        Var.mood = Mood.ILL;
                    }
                    if ((Var.currentTime - Var.illnessTimer) >= 6500) {
                        death();
                        illTimer.stop();
                    }
                }
            }
        };
        illTimer.start();
    }

    public void death() {
        Var.mood = Mood.DEAD;
    }
}

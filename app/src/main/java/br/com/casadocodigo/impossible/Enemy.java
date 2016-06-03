package br.com.casadocodigo.impossible;

import java.util.Random;

/**
 * Created by Gabriel on 03/06/2016.
 */
public class Enemy {
    Random random = new Random();
    private int radius = 10;
    private float ScreenMaxX;
    private float ScreenMaxY;
    private float coordX = 0;
    private float coordY = 0;

    public Enemy(float screenMaxX, float screenMaxY) {
        ScreenMaxX = screenMaxX;
        ScreenMaxY = screenMaxY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public float getScreenMaxX() {
        return ScreenMaxX;
    }

    public void setScreenMaxX(Float screenMaxX) {
        ScreenMaxX = screenMaxX;
    }

    public float getScreenMaxY() {
        return ScreenMaxY;
    }

    public void setScreenMaxY(Float screenMaxY) {
        ScreenMaxY = screenMaxY;
    }

    public void gerarPosicao(){
        Random random = new Random();
        coordX = random.nextFloat()%ScreenMaxX;
        coordY = random.nextFloat()%ScreenMaxY;
    }

    public void crescer(){
        this.radius++;
    }
}

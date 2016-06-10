package br.com.casadocodigo.impossible;

import java.util.Random;

/**
 * Created by Gabriel on 03/06/2016.
 */
public class Enemy {
    Random random = new Random();
    private boolean vivo = false;
    private boolean explodiu = false;
    private int radius = 100;
    private int pontuacao = 0;
    private float ScreenMaxX;
    private float ScreenMaxY;
    private float coordX = 500;
    private float coordY = 500;

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

    public boolean isVivo() { return vivo;}
    public void setVivo(boolean vivo) { this.vivo = vivo;}

    public boolean isExplodiu() { return explodiu;}
    public void setExplodiu(boolean explodiu) { this.explodiu = explodiu;}

    public int getPontuacao() { return pontuacao;}
    public void setPontuacao(int pontuacao) { this.pontuacao = pontuacao;}

    public void gerarPosicao(){
        this.vivo = true;
        this.pontuacao = 0;
        this.radius = 100;
        Random random = new Random();
        coordX = (random.nextFloat()*(ScreenMaxX - radius*2));
        coordY = (random.nextFloat()*(ScreenMaxY - radius*2));
    }

    public void diminuir(){
        if(this.radius > 0) {
            this.radius--;
            gerarPontuacao();
        } else
            this.explodiu = true;
            //this.vivo = false;
    }

    public void gerarPontuacao(){
        this.pontuacao++;
    }
}

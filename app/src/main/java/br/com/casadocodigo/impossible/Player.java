package br.com.casadocodigo.impossible;

import android.view.MotionEvent;

/**
 * Created by Gabriel on 01/06/2016.
 */
public class Player {

    public enum Direction{
        STAY,
        LEFT,
        RIGHT,
        UP,
        DOWN,
        NORTHEAST,
        NORTHWEST,
        SOUTHWEST,
        SOUTHEAST;
    }

    private float ScreenMaxX;
    private float ScreenMaxY;
    private int coordY = 300; // coordenada X do jogador
    private int coordX = 300; // coordenada Y do jogador
    private int radius = 50; // radio do circulo que o jogador ocupa
    private int mov = 10; // quantidade de pixels que o jogador se move

    public Player(float screenMaxX, float screenMaxY) {
        ScreenMaxX = screenMaxX;
        ScreenMaxY = screenMaxY;
    }

    public int getCoordY() {
        return coordY;
    }
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getRadius() { return radius; }
    public void setRadius(int radius) { this.radius = radius; }

    public int getMov() { return mov; }
    public void setMov(int mov) { this.mov = mov; }

    public float getScreenMaxX() { return ScreenMaxX; }
    public void setScreenMaxX(float screenMaxX) { ScreenMaxX = screenMaxX; }

    public float getScreenMaxY() {
        return ScreenMaxY;
    }

    public void setScreenMaxY(float screenMaxY) {
        ScreenMaxY = screenMaxY;
    }

    public void moveDown(int pixels){ coordY += pixels; }

    public void moveUp(int pixels){
        coordY -= pixels;
    }

    public void moveRight(int pixels){
        coordX += pixels;
    }

    public void moveLeft(int pixels){
        coordX -= pixels;
    }

    public Direction getDirection(float positionX, float positionY){
        //if(this.getCoordX()-radius > ScreenMaxX || this.getCoordY()-radius > ScreenMaxY || this.getCoordX()+50 < 0 || this.getCoordY()+50 < 0)
        //    return Direction.STAY;
        /*else*/ if(positionX < this.getCoordX()-radius && (positionY > this.getCoordY()-radius && positionY < this.getCoordY()+radius))
            return Direction.LEFT;
        else if(positionX > this.getCoordX()+radius && (positionY > this.getCoordY()-radius && positionY < this.getCoordY()+radius))
            return Direction.RIGHT;
        else if((positionX > this.getCoordX()-radius && positionX < this.getCoordX()+radius) && positionY > this.getCoordY()+radius)
            return Direction.DOWN;
        else if((positionX > this.getCoordX()-radius && positionX < this.getCoordX()+radius) && positionY < this.getCoordY()-radius)
            return Direction.UP;
        else if(positionX > this.getCoordX()+radius && positionY > this.getCoordY()+radius)
            return Direction.SOUTHEAST;
        else if(positionX > this.getCoordX()+radius && positionY < this.getCoordY()-radius)
            return Direction.NORTHEAST;
        else if(positionX < this.getCoordX()-radius && positionY > this.getCoordY()+radius)
            return Direction.SOUTHWEST;
        else if(positionX < this.getCoordX()-radius && positionY < this.getCoordY()-radius)
            return Direction.NORTHWEST;
        else
            return Direction.STAY;
    }
}

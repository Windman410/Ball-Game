package br.com.casadocodigo.impossible;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by K on 27/05/2016.
 */
public class Impossible extends SurfaceView implements Runnable {
    boolean running = false; //variavel para definir quando o jogo esta rodando
    Thread renderThread = null; // thread que vai renderizar a tela

    SurfaceHolder holder; //verifica se a superficie esta pronta para ser desenhada
    Paint paint; // classe para desenhar elementos na tela

    private int playerY = 300;

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    private int playerX = 300;

    public Impossible(Context context) {
        super(context);
        paint = new Paint();
        holder = getHolder();
    }
    // metodo para iniciar o jogo
    public void resume(){
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }
    // metodo para desenhar o player
    private void drawPlayer(Canvas canvas){
        paint.setColor(Color.CYAN);
        canvas.drawCircle(playerX,playerY,50,paint);
    }

    public void moveDown(int pixels){
        playerY += pixels;
    }

    public void moveUp(int pixels){
        playerY -= pixels;
    }

    public void moveRight(int pixels){
        playerX += pixels;
    }

    public void moveLeft(int pixels){
        playerX -= pixels;
    }

    public void run(){
        while (running){
            //verifica se a tela esta pronta para ser desenhada
            if(!holder.getSurface().isValid()){
                continue;
            }
            //bloqueia o canvas
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            //desenha o player
            this.drawPlayer(canvas);

            //atualiza e libera o canvas, depois pinta a tela de preto
            holder.unlockCanvasAndPost(canvas);
        }
    }
}

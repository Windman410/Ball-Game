package br.com.casadocodigo.impossible;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by K on 27/05/2016.
 */
public class Impossible extends SurfaceView implements Runnable {
    boolean running = false; //variavel para definir quando o jogo esta rodando
    Thread renderThread = null; // thread que vai renderizar a tela

    float screenWidth;
    float screenHeight;
    SurfaceHolder holder; //verifica se a superficie esta pronta para ser desenhada
    Paint paint; // classe para desenhar elementos na tela

    Player jogador;
    Enemy inimigo;
    int score;

    public Impossible(Context context) {
        super(context);
        paint = new Paint();
        holder = getHolder();
    }

    public Impossible(Context context, float width, float height) {
        super(context);
        paint = new Paint();
        holder = getHolder();
        screenWidth = width;
        screenHeight = height;
        jogador = new Player(screenWidth,screenHeight );
        inimigo = new Enemy(screenWidth,screenHeight );
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
        canvas.drawCircle(jogador.getCoordX(),jogador.getCoordY(),jogador.getRadius(),paint);
    }

    // metodo para desenhar o inimigo
    private void drawEnemy(Canvas canvas){
        paint.setColor(Color.GREEN);
        inimigo.diminuir();
        canvas.drawCircle(inimigo.getCoordX(),inimigo.getCoordY(),inimigo.getRadius(),paint);
    }

    public void moveDown(int pixels){ jogador.moveDown(pixels); }

    public void moveUp(int pixels){
        jogador.moveUp(pixels);
    }

    public void moveRight(int pixels){
        jogador.moveRight(pixels);
    }

    public void moveLeft(int pixels){
        jogador.moveLeft(pixels);
    }

    public int getMov(){ return jogador.getMov(); }

    public void detectarColisao(){
        double distancia = Math.pow(jogador.getCoordX() - inimigo.getCoordX(),2) +
                          Math.pow(jogador.getCoordY() - inimigo.getCoordY(), 2);
        distancia = Math.sqrt(distancia);
        // verifica distancia entre os raios
        if (distancia <= jogador.getRadius() + inimigo.getRadius()) {
            inimigo.setVivo(false);
            this.score += inimigo.getRadius();
        }
    }

    public void fimDeJogo(Canvas canvas){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
        paint.setTextSize(100);
        canvas.drawText("GAME OVER!\nSua pontuação é: " + this.score,50,150,paint);
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
            if(!inimigo.isVivo())
                inimigo.gerarPosicao();
            this.drawPlayer(canvas);
            this.drawEnemy(canvas);

            this.detectarColisao();

            /*if(inimigo.isExplodiu()){
               fimDeJogo(canvas);
               break;
            }*/

            //atualiza e libera o canvas, depois pinta a tela de preto
            holder.unlockCanvasAndPost(canvas);
        }
    }
}

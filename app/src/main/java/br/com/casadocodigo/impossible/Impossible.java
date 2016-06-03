package br.com.casadocodigo.impossible;

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

    DisplayMetrics metrics = new DisplayMetrics();

    SurfaceHolder holder; //verifica se a superficie esta pronta para ser desenhada
    Paint paint; // classe para desenhar elementos na tela

    Player jogador = new Player(metrics.widthPixels,metrics.heightPixels );
    Enemy inimigo = new Enemy(metrics.widthPixels,metrics.heightPixels );

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
        canvas.drawCircle(jogador.getCoordX(),jogador.getCoordY(),jogador.getRadius(),paint);
    }

    // metodo para desenhar o player
    private void drawEnemy(Canvas canvas){
        paint.setColor(Color.GREEN);
        inimigo.crescer();
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
            this.drawEnemy(canvas);

            //atualiza e libera o canvas, depois pinta a tela de preto
            holder.unlockCanvasAndPost(canvas);
        }
    }
}

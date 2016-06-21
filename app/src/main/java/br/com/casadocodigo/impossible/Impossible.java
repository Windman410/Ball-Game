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
        screenHeight = height - 100;
        jogador = new Player(screenWidth,screenHeight );
        inimigo = new Enemy(screenWidth,screenHeight );
    }
    // metodo para iniciar o jogo
    public void resume(){
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void inicializar(){
        jogador.setCoordX(screenWidth/2);
        jogador.setCoordY(screenHeight/2);
        jogador.setVivo(true);
        jogador.setRadius(50);
        jogador.setMov(10);
        jogador.setVidas(3);
        inimigo.gerarPosicao();
        inimigo.setExplodiu(false);

        this.score = 0;
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

    private void drawButtons(Canvas canvas){
        // Restart
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
        paint.setColor(Color.GREEN);
        canvas.drawRect(0,(screenHeight-100),(screenWidth/2),screenHeight,paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Restart",0, screenHeight-10, paint);
        // Exit
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setColor(Color.RED);
        canvas.drawRect((screenWidth/2),(screenHeight-100),(screenWidth),screenHeight,paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Exit", (screenWidth-100), screenHeight-10, paint);

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

    public boolean detectarColisao(){
        double distancia = Math.pow(jogador.getCoordX() - inimigo.getCoordX(),2) +
                          Math.pow(jogador.getCoordY() - inimigo.getCoordY(), 2);
        distancia = Math.sqrt(distancia);
        // verifica distancia entre os raios
        if (distancia <= jogador.getRadius() + inimigo.getRadius()) {
            return true;
        } else{
            return false;
        }
    }

    public void matarInimigo(){
        inimigo.setVivo(false);
        adicionarPontuacao(inimigo.getPontuacao());
    }

    public void fimDeJogo(Canvas canvas){
        jogador.setVivo(false);
        jogador.setRadius(0);
        jogador.setMov(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
        paint.setTextSize(50);
        canvas.drawText("GAME OVER!",100,(screenHeight/2)-50,paint);
        canvas.drawText("Sua pontuação é: ",50,(screenHeight/2),paint);
        canvas.drawText(String.valueOf(this.score),200,(screenHeight/2)+50,paint);
    }

    public void adicionarPontuacao(float pontuacao){
        this.score += pontuacao;
    }

    private void desenharPontuacao(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
        paint.setTextSize(50);
        canvas.drawText("Score:\n" + String.valueOf(score), 0, 50, paint);

        paint.setColor(Color.RED);
        if(jogador.getVidas() >= 1)
            canvas.drawCircle(20,100,20,paint);
        if(jogador.getVidas() > 1)
            canvas.drawCircle(70,100,20,paint);
        if(jogador.getVidas() > 2)
            canvas.drawCircle(120,100,20,paint);
    }


    public void run(){
        inicializar();
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
            do{
                inimigo.gerarPosicao();
            }while(this.detectarColisao());
            this.drawPlayer(canvas);
            this.drawEnemy(canvas);
            this.drawButtons(canvas);

            if(this.detectarColisao())
                if(jogador.isVivo())
                    this.matarInimigo();

            if(jogador.isVivo())
                this.desenharPontuacao(canvas);

            if(inimigo.isExplodiu()){
                jogador.perderVida();
            }

            if(jogador.getVidas() == 0){
                fimDeJogo(canvas);
                //break;
            }

            //atualiza e libera o canvas, depois pinta a tela de preto
            holder.unlockCanvasAndPost(canvas);
        }
    }
}

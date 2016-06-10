package br.com.casadocodigo.impossible;

import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Game extends AppCompatActivity implements View.OnTouchListener {
    Impossible view;
    public float screenWidth;
    public float screenHeight;
    AlertDialog exitDialog;
    AlertDialog restartDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//metodo para deixar a tela cheia
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        AlertDialog.Builder builder = criarBuilder("Deseja sair do jogo?", "Sim", "Não",0);
        exitDialog = builder.create();
        builder = criarBuilder("Deseja reiniciar o jogo?", "Sim", "Não",1);
        restartDialog = builder.create();
        view = new Impossible(this,screenWidth,screenHeight);
        view.setOnTouchListener(this);
        setContentView(view);


    }

    protected void onResume(){
        super.onResume();
        view.resume();
    }

    public AlertDialog.Builder criarBuilder(String titulo, String positivo, String negativo, final int flagAcao){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(titulo).setTitle("");
        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(flagAcao == 0)
                    System.exit(0);
                else if(flagAcao == 1)
                    view.inicializar();
            }
        });
        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        return builder;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getY() > (screenHeight - 180) && event.getX() < screenWidth / 2) {
            this.restartDialog.show();
        }

        if(event.getY() > (screenHeight - 180) && event.getX() > screenWidth / 2) {
            this.exitDialog.show();
        }

        switch (view.jogador.getDirection(event.getX(), event.getY())){

            case UP:
                view.moveUp(view.getMov());
                break;
            case DOWN:
                view.moveDown(view.getMov());
                break;
            case LEFT:
                view.moveLeft(view.getMov());
                break;
            case RIGHT:
                view.moveRight(view.getMov());
                break;
            case NORTHEAST:
                view.moveUp(view.getMov());
                view.moveRight(view.getMov());
                break;
            case NORTHWEST:
                view.moveUp(view.getMov());
                view.moveLeft(view.getMov());
                break;
            case SOUTHEAST:
                view.moveDown(view.getMov());
                view.moveRight(view.getMov());
                break;
            case SOUTHWEST:
                view.moveDown(view.getMov());
                view.moveLeft(view.getMov());
                break;
        }

            return true;
    }
}

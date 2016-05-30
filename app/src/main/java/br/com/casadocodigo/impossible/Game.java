package br.com.casadocodigo.impossible;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Game extends AppCompatActivity implements View.OnTouchListener {
    Impossible view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//metodo para deixar a tela cheia
        view = new Impossible(this);
        view.setOnTouchListener(this);
        setContentView(view);
    }

    protected void onResume(){
        super.onResume();
        view.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if((event.getX() > view.getPlayerX()-50 && event.getX() < view.getPlayerX()+50) && (event.getY() > view.getPlayerY()-50 && event.getY() < view.getPlayerY()+50))
            return true;
        else if(event.getX() < view.getPlayerX()-50 && (event.getY() > view.getPlayerY()-50 && event.getY() < view.getPlayerY()+50))
            view.moveLeft(10);
        else if(event.getX() > view.getPlayerX()+50 && (event.getY() > view.getPlayerY()-50 && event.getY() < view.getPlayerY()+50))
            view.moveRight(10);
        else if((event.getX() > view.getPlayerX()-50 && event.getX() < view.getPlayerX()+50) && event.getY() > view.getPlayerY()+50)
            view.moveDown(10);
        else if((event.getX() > view.getPlayerX()-50 && event.getX() < view.getPlayerX()+50) && event.getY() < view.getPlayerY()-50)
            view.moveUp(10);
        else if(event.getX() > view.getPlayerX()+50 && event.getY() > view.getPlayerY()+50) {
            view.moveRight(10);
            view.moveDown(10);
        }else if(event.getX() > view.getPlayerX()+50 && event.getY() < view.getPlayerY()-50) {
            view.moveRight(10);
            view.moveUp(10);
        }else if(event.getX() < view.getPlayerX()-50 && event.getY() > view.getPlayerY()+50) {
            view.moveLeft(10);
            view.moveDown(10);
        }else if(event.getX() < view.getPlayerX()-50 && event.getY() < view.getPlayerY()-50) {
            view.moveLeft(10);
            view.moveUp(10);
        }

            return true;
    }
}

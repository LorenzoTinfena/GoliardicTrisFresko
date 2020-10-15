package com.lorenzotinfena.goliardictrisfresko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final ImageButton[][] btns = new ImageButton[3][3];
    private Game game = new Game();

    private ImageView img_symbol;
    private TextView txt_victory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign buttons

        this.btns[0][0] = findViewById(R.id.ImageButton00);
        this.btns[1][0] = findViewById(R.id.ImageButton10);
        this.btns[2][0] = findViewById(R.id.ImageButton20);
        this.btns[0][1] = findViewById(R.id.ImageButton01);
        this.btns[1][1] = findViewById(R.id.ImageButton11);
        this.btns[2][1] = findViewById(R.id.ImageButton21);
        this.btns[0][2] = findViewById(R.id.ImageButton02);
        this.btns[1][2] = findViewById(R.id.ImageButton12);
        this.btns[2][2] = findViewById(R.id.ImageButton22);

        this.img_symbol = findViewById(R.id.img_symbol);
        this.txt_victory = findViewById(R.id.txt_victory);
        img_symbol.setImageResource(R.drawable.cross);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                final int i1 = i; //ez che sporca sta roba
                final int j1 = j; //
                this.btns[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_click(i1, j1, view);
                    }
                });
            }
        }

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }
/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // hideSystemUI
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }*/

    private final int[] crosses = {R.drawable.c0, R.drawable.c1, R.drawable.c2, R.drawable.c3};
    private final int[] noughts = {R.drawable.n0, R.drawable.n1, R.drawable.n2, R.drawable.n3};
    private int getRandomDrawableRes(Cell cell)
    {
        Random ran = new Random();
        if (cell == Cell.Cross)
            return crosses[ran.nextInt(crosses.length)];
        else
            return noughts[ran.nextInt(noughts.length)];
    }

    private Cell turno_attuale = Cell.Cross;
    private void btn_click(int i, int j, View view)
    {
        if (this.game.cells[i][j] == Cell.Empty)
        {
            ((ImageButton)view).setImageResource(getRandomDrawableRes(turno_attuale));
            if (game.move(i, j, turno_attuale))
                mostra_vittoria(turno_attuale);
            else
            {
                //change turn
                if (this.turno_attuale == Cell.Cross)
                    this.turno_attuale = Cell.Nought;
                else
                    this.turno_attuale = Cell.Cross;

                if (this.turno_attuale == Cell.Cross)
                    this.img_symbol.setImageResource(R.drawable.cross);
                else
                    this.img_symbol.setImageResource(R.drawable.nought);
            }
        }
    }
    private void mostra_vittoria(Cell cell)
    {
        if (cell == Cell.Cross)
            this.txt_victory.setText("Wins!");
    }
    private void reset()
    {
        this.game = new Game();
        this.turno_attuale = Cell.Cross;

        this.img_symbol.setImageResource(R.drawable.cross);
        this.txt_victory.setText("Turn");
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                this.btns[i][j].setImageResource(android.R.color.transparent);
            }
        }
    }
}
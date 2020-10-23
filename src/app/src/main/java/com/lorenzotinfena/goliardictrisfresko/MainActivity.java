package com.lorenzotinfena.goliardictrisfresko;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout cellsLinearLayout;
    private final ImageButton[][] btns = new ImageButton[3][3];
    private Game game = new Game();
    private ImageView simbolo_turno;
    private TextView txt_crosses_points;
    private TextView txt_noughts_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cellsLinearLayout = findViewById(R.id.cellsLinearLayout);

        findViewById(R.id.linear_layout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_layout_click();
            }
        });
        findViewById(R.id.linear_layout2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_layout_click();
            }
        });
        findViewById(R.id.linear_layout3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_layout_click();
            }
        });

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

        this.simbolo_turno = findViewById(R.id.simbolo_turno);
        this.txt_crosses_points = findViewById(R.id.txt_crosses_points);
        this.txt_noughts_points = findViewById(R.id.txt_noughts_points);

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
        System.out.println(this.btns[0][0].toString());
        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }
    private void linear_layout_click(){
        if (!this.inGame)
            next_round(false);
    }

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
    private Cell who_started_this_round = Cell.Cross;
    private boolean inGame = true;
    private void btn_click(int i, int j, View view)
    {
        if (inGame){
            if (this.game.cells[i][j] == null) {
                MoveResult res = game.move(i, j, turno_attuale);
                if (res != MoveResult.Draw)
                    ((ImageButton)view).setImageResource(getRandomDrawableRes(turno_attuale));
                switch (res){
                    case Win:
                        mostra_vittoria();
                        break;
                    case Draw:
                        next_round(false);
                        break;
                    case Continue:
                        //change turn
                        if (this.turno_attuale == Cell.Cross)
                            this.turno_attuale = Cell.Nought;
                        else
                            this.turno_attuale = Cell.Cross;

                        sposta_simbolo_turno();
                        break;
                }
            }
        }
        else{
            next_round(false);
        }
    }
    private void sposta_simbolo_turno(){

        Resources r = getResources();
        int f = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45,   r.getDisplayMetrics());

        ObjectAnimator animator;
        if (this.turno_attuale == Cell.Cross)
            animator = ObjectAnimator.ofFloat(this.simbolo_turno, "TranslationX", 0f);
        else
            animator = ObjectAnimator.ofFloat(this.simbolo_turno, "TranslationX", f);
        animator.setDuration(300);
        animator.start();
    }
    private void mostra_vittoria()
    {
        // ANIMAZIONE
        AnimatorSet animatorSet = new AnimatorSet();
        for (int i = 0; i < 3; i++){
            here:
            for (int j = 0; j < 3; j++){
                // BREAK IF IS A VICTORY CELL
                for (int z = 0; z < 3; z++){
                    if (new Point(i, j).equals(this.game.pointsVictory[z]))
                        continue here;
                }
                ObjectAnimator animatorx = ObjectAnimator.ofFloat(this.btns[i][j], "ScaleX", 0.7f);
                ObjectAnimator animatory = ObjectAnimator.ofFloat(this.btns[i][j], "ScaleY", 0.7f);
                animatorSet.playTogether(animatorx);
                animatorSet.playTogether(animatory);
            }
        }
        animatorSet.setDuration(500);
        animatorSet.start();

        if (this.game.cell_victory == Cell.Cross)
            SetPoints(Cell.Cross, GetPoints(Cell.Cross) + 1);
        else
            SetPoints(Cell.Nought, GetPoints(Cell.Nought) + 1);

        inGame = false;
    }
    private void next_round(boolean is_resetting){
        if (is_resetting || who_started_this_round == Cell.Nought)
            this.turno_attuale = Cell.Cross;
        else
            this.turno_attuale = Cell.Nought;
        sposta_simbolo_turno();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
                this.btns[i][j].setImageResource(android.R.color.transparent);
        }

        if (this.game.pointsVictory[0] != null){ //if the game is not won
            for (int a = 0; a < 3; a++){
                int i = this.game.pointsVictory[a].x, j = this.game.pointsVictory[a].y;
                this.btns[i][j].setScaleX(0.7f);
                this.btns[i][j].setScaleY(0.7f);
                this.btns[j][2 - i].setScaleX(1f);
                this.btns[j][2 - i].setScaleY(1f);
            }
        }

        this.cellsLinearLayout.setRotation(-90f);

        // ANIMAZIONE
        AnimatorSet animatorSet = new AnimatorSet();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                ObjectAnimator animatorx = ObjectAnimator.ofFloat(this.btns[i][j], "ScaleX", 1f);
                ObjectAnimator animatory = ObjectAnimator.ofFloat(this.btns[i][j], "ScaleY", 1f);
                animatorSet.playTogether(animatorx);
                animatorSet.playTogether(animatory);
            }
        }

        ObjectAnimator animatorCells = ObjectAnimator.ofFloat(this.cellsLinearLayout, "Rotation", 0f);
        animatorSet.playTogether(animatorCells);
        animatorSet.setDuration(700);
        animatorSet.start();

        this.game = new Game();
        inGame = true;
    }
    private void reset()
    {
        next_round(true);
        SetPoints(Cell.Cross, 0);
        SetPoints(Cell.Nought, 0);
        txt_noughts_points.setText("0");
    }
    private int GetPoints(Cell cell){
        if (cell == Cell.Cross)
            return Integer.parseInt(txt_crosses_points.getText().toString());
        else
            return Integer.parseInt(txt_noughts_points.getText().toString());
    }
    private void SetPoints(Cell cell, int points){
        if (cell == Cell.Cross)
            txt_crosses_points.setText(Integer.toString(points));
        else
            txt_noughts_points.setText(Integer.toString(points));
    }
}
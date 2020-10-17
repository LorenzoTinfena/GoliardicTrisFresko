package com.lorenzotinfena.goliardictrisfresko;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cellsLinearLayout = findViewById(R.id.cellsLinearLayout);

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
    private boolean inGame = true;
    private void btn_click(int i, int j, View view)
    {
        if (inGame){
            if (this.game.cells[i][j] == null) {
                ((ImageButton)view).setImageResource(getRandomDrawableRes(turno_attuale));
                switch (game.move(i, j, turno_attuale)){
                    case Win:
                        mostra_vittoria();
                        break;
                    case Draw:
                        reset();
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


        // TODO
        inGame = false;
    }
    private void reset()
    {
        this.turno_attuale = Cell.Cross;
        sposta_simbolo_turno();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
                this.btns[i][j].setImageResource(android.R.color.transparent);
        }

        if (this.game.pointsVictory[0] != null){
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
}
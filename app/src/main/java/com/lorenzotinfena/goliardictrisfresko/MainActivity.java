package com.lorenzotinfena.goliardictrisfresko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private final ImageButton[][] btns = new ImageButton[3][3];
    private Game game = new Game();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign buttons
        btns[0][0] = findViewById(R.id.ImageButton00);
        btns[1][0] = findViewById(R.id.ImageButton10);
        btns[2][0] = findViewById(R.id.ImageButton20);
        btns[0][1] = findViewById(R.id.ImageButton01);
        btns[1][1] = findViewById(R.id.ImageButton11);
        btns[2][1] = findViewById(R.id.ImageButton21);
        btns[0][2] = findViewById(R.id.ImageButton02);
        btns[1][2] = findViewById(R.id.ImageButton12);
        btns[2][2] = findViewById(R.id.ImageButton22);

        game.move(0, 0, true);
    }

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
    }

}
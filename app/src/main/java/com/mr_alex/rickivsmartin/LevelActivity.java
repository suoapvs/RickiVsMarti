package com.mr_alex.rickivsmartin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LevelActivity extends Activity {

    private Sound sound;
    private int setButtonSound;
    private ImageButton bSound;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level);

        AssetManager am = getAssets();
        sound = new Sound(am, 1);
        setButtonSound = sound.loadSound("Schelchok1.mp3");
        sound.setCheckSound(true);

        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "font.ttf");

        Button bNewGame2 = (Button) findViewById(R.id.bLevel2);
        bNewGame2.setOnClickListener(onClickListener);
        bNewGame2.setTypeface(myFont);
        Button bNewGame4 = (Button) findViewById(R.id.bLevel4);
        bNewGame4.setOnClickListener(onClickListener);
        bNewGame4.setTypeface(myFont);
        Button bNewGame6 = (Button) findViewById(R.id.bLevel6);
        bNewGame6.setOnClickListener(onClickListener);
        bNewGame6.setTypeface(myFont);

        try {
            sound.setCheckSound(getIntent().getExtras().getBoolean("checkSound"));
        } catch(Exception e) {
            sound.setCheckSound(true);
        }

        bSound = (ImageButton) this.findViewById(R.id.bSoundOffOn);
        bSound.setOnClickListener(onClickListener);
        if (sound.getCheckSound())
            bSound.setImageResource(R.drawable.soundon);
        else bSound.setImageResource(R.drawable.soundoff);

        ImageButton bBackMenu = (ImageButton) this.findViewById(R.id.bBackMenu);
        bBackMenu.setOnClickListener(onClickListener);

        TextView tGame = (TextView) findViewById(R.id.tGame);
        tGame.setTypeface(myFont);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bLevel2:
                    newGame(2);
                    break;
                case R.id.bLevel4:
                    newGame(4);
                    break;
                case R.id.bLevel6:
                    newGame(6);
                    break;
                case R.id.bSoundOffOn:
                    soundOffOn();
                    break;
                case R.id.bBackMenu:
                    backMenu();
                    break;
                default:
                    break;
            }
            sound.playSound(setButtonSound);
        }
    };

    private void newGame(int level) {
        Intent intent = new Intent();
        switch (level) {
            case 2:
                intent = new Intent(LevelActivity.this, Game2Activity.class);
                break;
            case 4:
                intent = new Intent(LevelActivity.this, GameActivity.class);
                break;
            case 6:
                intent = new Intent(LevelActivity.this, Game6Activity.class);
                break;
        }

        intent.putExtra("checkSound", sound.getCheckSound());
        intent.putExtra("keyGame", level);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void soundOffOn() {
        sound.setCheckSound(!sound.getCheckSound());
        if (sound.getCheckSound())
            bSound.setImageResource(R.drawable.soundon);
        else bSound.setImageResource(R.drawable.soundoff);
    }

    public void backMenu() {
        Intent intent = new Intent(LevelActivity.this, MenuActivity.class);
        intent.putExtra("checkSound", sound.getCheckSound());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}

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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuActivity extends Activity {

    private Sound sound;
    private int setButtonSound, dogSound, catSound;
    private ImageButton bSound;

    private TextView tDog, tCat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        AssetManager am = getAssets();
        sound = new Sound(am, 2);
        setButtonSound = sound.loadSound("Schelchok1.mp3");
        catSound = sound.loadSound("cat1.mp3");
        dogSound = sound.loadSound("dog1.mp3");

        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "font.ttf");

        Button bNewGame = (Button) findViewById(R.id.bNewGame);
        bNewGame.setTypeface(myFont);
        bNewGame.setOnClickListener(onClickListener);
        Button bСontinue = (Button) findViewById(R.id.bСontinue);
        bСontinue.setTypeface(myFont);
        bСontinue.setOnClickListener(onClickListener);
        Button bHelpGame = (Button) findViewById(R.id.bHelpGame);
        bHelpGame.setTypeface(myFont);
        bHelpGame.setOnClickListener(onClickListener);
        Button bExitGame = (Button) findViewById(R.id.bExitGame);
        bExitGame.setTypeface(myFont);
        bExitGame.setOnClickListener(onClickListener);

        Typeface myFont2 = Typeface.createFromAsset(this.getAssets(), "comic.ttf");
        Button bDog = (Button) findViewById(R.id.bDog);
        bDog.setOnClickListener(onClickListener);
        Button bCat = (Button) findViewById(R.id.bCat);
        bCat.setOnClickListener(onClickListener);
        tDog = (TextView) findViewById(R.id.tDog);
        tDog.setTypeface(myFont2);
        tCat = (TextView) findViewById(R.id.tCat);
        tCat.setTypeface(myFont2);


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

        TextView tRicki = (TextView) findViewById(R.id.tRicki);
        tRicki.setTypeface(myFont);
        TextView tVS = (TextView) findViewById(R.id.tVS);
        tVS.setTypeface(myFont);
        TextView tMartin = (TextView) findViewById(R.id.tMartin);
        tMartin.setTypeface(myFont);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bNewGame:
                    newGame();
                    sound.playSound(setButtonSound);
                    break;
                case R.id.bСontinue:
                    continueGame();
                    sound.playSound(setButtonSound);
                    break;
                case R.id.bHelpGame:
                    helpGame();
                    sound.playSound(setButtonSound);
                    break;
                case R.id.bExitGame:
                    exitGame();
                    sound.playSound(setButtonSound);
                    break;
                case R.id.bSoundOffOn:
                    soundOffOn();
                    sound.playSound(setButtonSound);
                    break;
                case R.id.bDog:
                    tDog.setText(R.string.nameDog);
                    sound.playSound(dogSound);
                    break;
                case R.id.bCat:
                    tCat.setText(R.string.nameCat);
                    sound.playSound(catSound);
                    break;
                default:
                    break;
            }
        }
    };

    private void newGame() {
        Intent intent = new Intent(MenuActivity.this, LevelActivity.class);
        intent.putExtra("checkSound", sound.getCheckSound());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void continueGame() {
        try{
            Intent intent = new Intent(MenuActivity.this, GameActivity.class);
            switch (Integer.parseInt(readFile("fileN"))) {
                case 2:
                    intent = new Intent(MenuActivity.this, Game2Activity.class);
                    break;
                case 4:
                    intent = new Intent(MenuActivity.this, GameActivity.class);
                    break;
                case 6:
                    intent = new Intent(MenuActivity.this, Game6Activity.class);
                    break;
            }
            intent.putExtra("checkSound", sound.getCheckSound());
            intent.putExtra("keyGame", 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } catch(Exception e) {
            Toast.makeText(MenuActivity.this, R.string.notSave, Toast.LENGTH_SHORT).show();
        }
    }

    private void helpGame() {
        Intent intent = new Intent(MenuActivity.this, HelpActivity.class);
        intent.putExtra("checkSound", sound.getCheckSound());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void exitGame() {
        super.onDestroy();
        finish();
    }

    public void soundOffOn() {
        sound.setCheckSound(!sound.getCheckSound());
        if (sound.getCheckSound())
            bSound.setImageResource(R.drawable.soundon);
        else bSound.setImageResource(R.drawable.soundoff);
    }

    public String readFile(String FILENAME) {
        String text;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            text = br.readLine();
        } catch(IOException e) {
            text = "0";
        }
        return text;
    }
}


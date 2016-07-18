package com.mr_alex.rickivsmartin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.ImageButton;
import android.content.Intent;

public class Game6Activity extends Activity {

    private final int N = 6;
    Cards cards;
    private ImageButton[][] but;
    private final int but_id[][] = {{R.id.b00, R.id.b01, R.id.b02, R.id.b03, R.id.b04, R.id.b05},
            {R.id.b10, R.id.b11, R.id.b12, R.id.b13, R.id.b14, R.id.b15},
            {R.id.b20, R.id.b21, R.id.b22, R.id.b23, R.id.b24, R.id.b25},
            {R.id.b30, R.id.b31, R.id.b32, R.id.b33, R.id.b34, R.id.b35},
            {R.id.b40, R.id.b41, R.id.b42, R.id.b43, R.id.b44, R.id.b45},
            {R.id.b50, R.id.b51, R.id.b52, R.id.b53, R.id.b54, R.id.b55}},

            dog_id[][] = {{R.drawable.dog601, R.drawable.dog604, R.drawable.dog606, R.drawable.dog602, R.drawable.dog605, R.drawable.dog603},
            {R.drawable.dog602, R.drawable.dog605, R.drawable.dog603, R.drawable.dog606, R.drawable.dog604, R.drawable.dog601},
            {R.drawable.dog601, R.drawable.dog604, R.drawable.dog606, R.drawable.dog602, R.drawable.dog605, R.drawable.dog603},
            {R.drawable.dog602, R.drawable.dog605, R.drawable.dog603, R.drawable.dog606, R.drawable.dog604, R.drawable.dog601},
            {R.drawable.dog601, R.drawable.dog604, R.drawable.dog606, R.drawable.dog602, R.drawable.dog605, R.drawable.dog603},
            {R.drawable.dog602, R.drawable.dog605, R.drawable.dog603, R.drawable.dog606, R.drawable.dog604, R.drawable.dog601}},

            cat_id[][] = {{R.drawable.cat601, R.drawable.cat605, R.drawable.cat604, R.drawable.cat603, R.drawable.cat602, R.drawable.cat606},
            {R.drawable.cat606, R.drawable.cat602, R.drawable.cat605, R.drawable.cat604, R.drawable.cat603, R.drawable.cat601},
            {R.drawable.cat601, R.drawable.cat605, R.drawable.cat604, R.drawable.cat603, R.drawable.cat602, R.drawable.cat606},
            {R.drawable.cat606, R.drawable.cat602, R.drawable.cat605, R.drawable.cat604, R.drawable.cat603, R.drawable.cat601},
            {R.drawable.cat601, R.drawable.cat605, R.drawable.cat604, R.drawable.cat603, R.drawable.cat602, R.drawable.cat606},
            {R.drawable.cat606, R.drawable.cat602, R.drawable.cat605, R.drawable.cat604, R.drawable.cat603, R.drawable.cat601}};

    private boolean check;

    private Sound sound;
    private int clickCat, clickDog, setButtonSound, victorySound, dogSound, catSound;
    private ImageButton bSound;

    private TextView tScore, tBestScore;
    private int numbSteps, numbBestSteps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game6);

        but = new ImageButton[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                but[i][j] = (ImageButton) this.findViewById(but_id[i][j]);
                but[i][j].setOnClickListener(onClickListener);
            }

        ImageButton bNewGame = (ImageButton) this.findViewById(R.id.bNewGame);
        bNewGame.setOnClickListener(onClickListener);
        ImageButton bExitGame = (ImageButton) this.findViewById(R.id.bBackMenu);
        bExitGame.setOnClickListener(onClickListener);
        ImageButton bAbout = (ImageButton) this.findViewById(R.id.bAboutGame);
        bAbout.setOnClickListener(onClickListener);
        bSound = (ImageButton) this.findViewById(R.id.bSoundOffOn);
        bSound.setOnClickListener(onClickListener);

        Typeface myFont = Typeface.createFromAsset(this.getAssets(), "comic.ttf");
        Typeface myFont2 = Typeface.createFromAsset(this.getAssets(), "font.ttf");
        TextView tSScore = (TextView) this.findViewById(R.id.tSScore);
        tSScore.setTypeface(myFont2);
        tScore = (TextView) this.findViewById(R.id.tScore);
        tScore.setTypeface(myFont);
        TextView tBest = (TextView) this.findViewById(R.id.tBest);
        tBest.setTypeface(myFont2);
        tBestScore = (TextView) this.findViewById(R.id.tBestScore);
        tBestScore.setTypeface(myFont);

        AssetManager am = getAssets();
        sound = new Sound(am, 3);
        setButtonSound = sound.loadSound("Schelchok1.mp3");
        victorySound = sound.loadSound("Victory.mp3");
        clickCat = sound.loadSound("cat.mp3");
        clickDog = sound.loadSound("dog.mp3");
        catSound = sound.loadSound("cat1.mp3");
        dogSound = sound.loadSound("dog1.mp3");

        try {
            sound.setCheckSound(getIntent().getExtras().getBoolean("checkSound"));
        } catch(Exception e) {
            sound.setCheckSound(true);
        }

        cards = new Cards(N, N);
        try {
            if(getIntent().getExtras().getInt("keyGame") == 1) {
                continueGame();
                checkFinish();
            } else newGame();
        } catch (Exception e) {
            newGame();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (v.getId() == but_id[i][j]) {
                        if (!check) {
                            if (cards.getValueBoard(i, j))
                                sound.playSound(clickDog);
                            else sound.playSound(clickCat);
                            ButFunc(i, j);
                        }
                        else if (cards.valueControl() == N * N)
                            sound.playSound(dogSound);
                        else
                            sound.playSound(catSound);
                    }

            switch (v.getId()) {
                case R.id.bNewGame:
                    sound.playSound(setButtonSound);
                    newGame();
                    break;
                case R.id.bBackMenu:
                    sound.playSound(setButtonSound);
                    backMenu();
                    break;
                case R.id.bAboutGame:
                    sound.playSound(setButtonSound);
                    aboutOnClick();
                    break;
                case R.id.bSoundOffOn:
                    soundOffOn();
                    sound.playSound(setButtonSound);
                    break;
                default:
                    break;
            }
        }
    };

    private void ButFunc(int row, int column) {
        cards.moveCards(row, column);
        numbSteps++;
        showGame();
        checkFinish();
    }

    private void newGame() {
        cards.getNewCards();
        numbSteps = 0;
        numbBestSteps = Integer.parseInt(readFile("fbs6"));
        tBestScore.setText(Integer.toString(numbBestSteps));
        showGame();
        check = false;
    }

    private void continueGame() {
        String text = getPreferences(MODE_PRIVATE).getString("save6", "");
        int k = 0;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++) {
                if(Integer.parseInt("" + text.charAt(k)) == 1)
                    cards.setValueBoard(i, j, true);
                else cards.setValueBoard(i, j, false);
                k++;
            }

        numbSteps = Integer.parseInt(readFile("fs6"));
        numbBestSteps = Integer.parseInt(readFile("fbs6"));
        tBestScore.setText(Integer.toString(numbBestSteps));

        showGame();
        check = false;
    }

    private void backMenu() {
        saveValueBoard();
        Intent intent = new Intent(Game6Activity.this, MenuActivity.class);
        intent.putExtra("checkSound", sound.getCheckSound());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void aboutOnClick() {
        Intent intent = new Intent(Game6Activity.this, AboutActivity.class);
        startActivity(intent);
    }

    private void saveValueBoard() {
        String text = "";
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++) {
                if(cards.getValueBoard(i, j))
                    text += "1";
                else text += "0";
            }

        getPreferences(MODE_PRIVATE).edit().putString("save6", text).commit();
        writeFile(Integer.toString(numbSteps), "fs6");
        writeFile(Integer.toString(N), "fileN");
    }

    private void showGame() {
        tScore.setText(Integer.toString(numbSteps));

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (cards.getValueBoard(i, j)) {
                    but[i][j].setImageResource(dog_id[i][j]);
                } else {
                    but[i][j].setImageResource(cat_id[i][j]);
                }
            }

        if (sound.getCheckSound())
            bSound.setImageResource(R.drawable.soundon);
        else bSound.setImageResource(R.drawable.soundoff);
    }

    private void checkFinish() {
        if(cards.finished()) {
            showGame();
            sound.playSound(victorySound);
            Toast.makeText(Game6Activity.this, R.string.you_won, Toast.LENGTH_SHORT).show();
            if ((numbSteps < numbBestSteps) || (numbBestSteps == 0)) {
                writeFile(Integer.toString(numbSteps), "fbs6");
                tBestScore.setText(Integer.toString(numbSteps));
            }
            check = true;
        }
    }

    public void soundOffOn() {
        sound.setCheckSound(!sound.getCheckSound());
        showGame();
    }

    public void writeFile(String text, String FILENAME) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME, MODE_PRIVATE)));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String FILENAME) {
        String text;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            text = br.readLine();
        } catch (IOException e) {
            text = "0";
        }
        return text;
    }
}


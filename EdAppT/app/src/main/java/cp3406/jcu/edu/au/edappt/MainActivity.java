package cp3406.jcu.edu.au.edappt;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static cp3406.jcu.edu.au.edappt.Settings.SETTINGS_REQUEST;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    public String gameTime = "10";
    public String gameDifficulty = "Easy";
    public String gameName = "Anonymous";
    public Boolean gameSound = TRUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            gameTime = savedInstanceState.getString("gameTime");
            gameDifficulty = savedInstanceState.getString("gameDifficulty");
            gameSound = savedInstanceState.getBoolean("gameSound", Boolean.TRUE);
            gameName = savedInstanceState.getString("gameName");
        }
    }

    // Save state of passed variable on pause or stop
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("gameTime", gameTime);
        outState.putString("gameDifficulty", gameDifficulty);
        outState.putBoolean("gameSound", gameSound);
        outState.putString("gameName", gameName);
    }

    // Check for a requests from settings page and store it in passed variable
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTINGS_REQUEST){
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    gameTime = data.getStringExtra("gameTime");
                    gameDifficulty = data.getStringExtra("gameDifficulty");
                    gameSound = data.getBooleanExtra("gameSound", Boolean.TRUE);
                    gameName = data.getStringExtra("gameName");
                }
            }
        }
    }

    // Open Settings
    public void openSettings(View v){
        Intent intent = new Intent(this, cp3406.jcu.edu.au.edappt.Settings.class);
        intent.putExtra("gameTime", gameTime);
        intent.putExtra("gameDifficulty", gameDifficulty);
        intent.putExtra("gameSound", gameSound);
        intent.putExtra("gameName", gameName);
        startActivityForResult(intent, Settings.SETTINGS_REQUEST);
    }

    // Open HighScores
    public void openHighscore(View v){
        Intent intent = new Intent(this, cp3406.jcu.edu.au.edappt.HighScore.class);
        startActivity(intent);
    }

    //Open a Game
    public void openGame(View v){
        Intent intent = new Intent(this, cp3406.jcu.edu.au.edappt.GameActivity.class);
        intent.putExtra("gameTime", gameTime);
        intent.putExtra("gameDifficulty", gameDifficulty);
        intent.putExtra("gameSound", gameSound);
        intent.putExtra("gameName", gameName);
        startActivity(intent);
    }
}
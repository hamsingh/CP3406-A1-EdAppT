package cp3406.jcu.edu.au.edappt;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;
import java.util.Locale;

import cp3406.jcu.edu.au.edappt.game.Game;
import cp3406.jcu.edu.au.edappt.game.QuestionManager;

public class GameActivity extends AppCompatActivity implements StateListener {

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private ShakeDetector mShakeDetector;

    // Fragments
    private GameFragment gameFragment;
    private StatusFragment statusFragment;

    // Timer
    private CountDownTimer timer;

    // Intent extras from main activity
    public String gameTime;
    public String gameDifficulty;
    public Boolean gameSound;
    public String gameName;

    // Database
    private Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // get intent extra
        Intent intent = getIntent();
        gameTime = intent.getStringExtra("gameTime");
        gameDifficulty = intent.getStringExtra("gameDifficulty");
        gameSound = intent.getBooleanExtra("gameSound", Boolean.TRUE);
        gameName = intent.getStringExtra("gameName");

        if (savedInstanceState == null) {
            // Instance of first fragment
            gameFragment = new GameFragment ();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();    // begin  FragmentTransaction
            ft.add(R.id.game, gameFragment);                                            // add    Fragment
            ft.commit();                                                                // commit FragmentTransaction

            statusFragment = new StatusFragment ();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            statusFragment.setArguments(args);                                          // (1) Communicate with Fragment using Bundle
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();   // begin  FragmentTransaction
            ft2.add(R.id.status, statusFragment);                                       // add    Fragment
            ft2.commit();
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            statusFragment = new StatusFragment ();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            statusFragment.setArguments(args);                                          // (1) Communicate with Fragment using Bundle
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();   // begin  FragmentTransaction
            ft2.add(R.id.status, statusFragment);                                       // add    Fragment
            ft2.commit();                                                               // commit FragmentTransaction
        }

        // initialise sensor manager and shake detector
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mShakeDetector = new ShakeDetector(() -> {
            gameFragment.newGame();
            mShakeDetector.stop(); // stop listener once triggered
        });
        mShakeDetector.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT); // set shake sensitivity
    }

    public void onUpdate(State state) {
        String text = String.format(Locale.getDefault(), "state: %s level: %s", state, gameDifficulty);
        Log.i("GameActivity", text);

        // state switcher
        switch(state) {
            case START_GAME:
                gameFragment.enableSound(gameSound);
                gameFragment.setVisibility(true);
                statusFragment.setVisibility(true);
                Game game = new Game();
                QuestionManager questionManager = new QuestionManager(gameDifficulty);
                gameFragment.setGame(game, questionManager);
                enableTimer();
                break;
            case CONTINUE_GAME:
                statusFragment.setScore(gameFragment.getScore()); // set score
                gameFragment.newQuestion(); // create and display new question
                break;
            case GAME_OVER:
                timer.cancel();
                statusFragment.setFinalScore(gameFragment.getScore());
                statusFragment.endScreen();
                gameFragment.endScreen();
                mShakeDetector.stop();
                break;
        }
    }

    public void enableTimer() {
        int timerSeconds;
        switch (gameTime) {
            case "10":
                timerSeconds = 10;
                break;
            case "30":
                timerSeconds = 30;
                break;
            case "60":
                timerSeconds = 60;
                break;
            case "90":
                timerSeconds = 90;
                break;
            default:
                timerSeconds = 30;
        }

        timer = new CountDownTimer((timerSeconds+1) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                statusFragment.setMessage(String.format(Locale.getDefault(),
                        "Time Remaining: %d", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                onUpdate(State.GAME_OVER);
            }
        };
        timer.start();
    }

    // start shake detector
    @Override
    protected void onResume() {
        super.onResume();
        mShakeDetector.start(mSensorManager);
    }

    // onClick for new game button
    public void openNewGame(View v) {
        statusFragment.newGame(v);
    }

    // onClick for score share to twitter
    public void openShareScore(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.ENGLISH, "https://twitter.com/intent/tweet?text=I just got %d questions right on EdAppTMath!", gameFragment.getScore())));
        startActivity(browserIntent);
    }

    // onClick for saving high score to high scores
    public void openSaveScore(View v) {
        database.addData(gameName, gameDifficulty, gameTime, Integer.toString(gameFragment.getScore()));
        Toast.makeText(getApplicationContext(), "Score saved to HighScores!",Toast.LENGTH_SHORT).show();
    }
}


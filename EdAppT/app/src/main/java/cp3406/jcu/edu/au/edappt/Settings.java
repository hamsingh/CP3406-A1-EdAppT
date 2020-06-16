package cp3406.jcu.edu.au.edappt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;


public class Settings extends AppCompatActivity {

    static int SETTINGS_REQUEST = 1;
    private Spinner difficultySpinner;
    private Spinner timeSpinner;
    private Switch soundSwitch;
    private EditText usernameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        timeSpinner = findViewById(R.id.timeSpinner);
        soundSwitch = findViewById(R.id.soundSwitch);
        usernameEdit = findViewById(R.id.userName);

        // Get intent and read intent
        Intent intent = getIntent();
        String gameTime = intent.getStringExtra("gameTime");
        String gameDifficulty = intent.getStringExtra("gameDifficulty");
        Boolean gameSound = intent.getBooleanExtra("gameSound", Boolean.TRUE);
        String userName = intent.getStringExtra("gameName");

        // Apply intents to spinners to show previous settings
        timeSpinner.setSelection(((ArrayAdapter<String>)timeSpinner.getAdapter()).getPosition(gameTime));
        difficultySpinner.setSelection(((ArrayAdapter<String>)difficultySpinner.getAdapter()).getPosition(gameDifficulty));
        soundSwitch.setChecked(gameSound);
        usernameEdit.setText(userName);
    }

    // Send users choice with intent for usage of other activities
    public void applySettings(View v){
        String resultTime = timeSpinner.getSelectedItem().toString();
        String resultDifficulty = difficultySpinner.getSelectedItem().toString();
        Boolean resultSound = soundSwitch.isChecked();
        String resultUsername = usernameEdit.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("gameTime", resultTime);
        resultIntent.putExtra("gameDifficulty", resultDifficulty);
        resultIntent.putExtra("gameSound", resultSound);
        resultIntent.putExtra("gameName", resultUsername);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

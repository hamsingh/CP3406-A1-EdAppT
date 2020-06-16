package cp3406.jcu.edu.au.edappt;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    private Database database = new Database(this);

    // recycler view to display highscores
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        recyclerView = findViewById(R.id.highscores);

        buildRecyclerView();
    }

    //builds the view by accessing the database and making a new card for each row in the main table
    public void buildRecyclerView() {

        Cursor data = database.getData();

        ArrayList<HighScoreItem> highscores = new ArrayList<>();

        for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
            highscores.add(new HighScoreItem(data.getString(data.getColumnIndex("Name")), data.getString(data.getColumnIndex("Difficulty")), data.getString(data.getColumnIndex("Time")), data.getString(data.getColumnIndex("Score"))));
        }

        HighScoreAdapter recyclerAdapter;
        RecyclerView.LayoutManager recyclerLayoutManager;
        recyclerLayoutManager = new LinearLayoutManager(this);

        recyclerAdapter = new HighScoreAdapter(highscores);

        recyclerView.setLayoutManager(recyclerLayoutManager);

        recyclerView.setAdapter(recyclerAdapter);
    }
}

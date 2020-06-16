package cp3406.jcu.edu.au.edappt;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {
    private StateListener listener;
    private TextView time;
    private TextView score;
    private Button newGame;
    private Button shareScore;
    private Button saveScore;
    private TextView finalScore;

    // attaches listener
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener = (StateListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_status, container, false);

        time = view.findViewById(R.id.time);
        score = view.findViewById(R.id.score);
        finalScore = view.findViewById(R.id.finalScore);
        newGame = view.findViewById(R.id.newGame);
        saveScore = view.findViewById(R.id.saveScore);
        shareScore = view.findViewById(R.id.shareScore);

        return view;
    }

    // set timer text
    public void setMessage(String text) {
        time.setText(text);
    }

    // set score
    public void setScore(int text) {
        score.setText(String.format(Locale.ENGLISH, "Score: %d", text));
    }

    // set final score on final end screen
    public void setFinalScore(int text) {
        finalScore.setText(String.format(Locale.ENGLISH, "You Scored: %d!", text));
    }

    // set visibility for status entities
    public void setVisibility(boolean isVisible) {
        if(isVisible) {
            time.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
        }
        else {
            time.setVisibility(View.INVISIBLE);
            score.setVisibility(View.INVISIBLE);
        }
    }

    // set visibility for end screen entities
    public void endScreen() {
        time.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        finalScore.setVisibility(View.VISIBLE);
        newGame.setVisibility(View.VISIBLE);
        saveScore.setVisibility(View.VISIBLE);
        shareScore.setVisibility(View.VISIBLE);
    }

    // set visibility for new game
    public void newGame(View v) {
        finalScore.setVisibility(View.INVISIBLE);
        newGame.setVisibility(View.INVISIBLE);
        saveScore.setVisibility(View.INVISIBLE);
        shareScore.setVisibility(View.INVISIBLE);
        setScore(0);
        listener.onUpdate(State.START_GAME);
    }
}

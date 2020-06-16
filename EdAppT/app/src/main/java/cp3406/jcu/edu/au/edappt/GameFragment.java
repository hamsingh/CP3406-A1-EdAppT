package cp3406.jcu.edu.au.edappt;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import cp3406.jcu.edu.au.edappt.game.Game;
import cp3406.jcu.edu.au.edappt.game.QuestionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    // sound player
    private SoundPlayer soundPlayer;
    private Boolean gameSound;

    // recycler view to display answers
    private RecyclerView recyclerView;

    private StateListener listener;
    private Game currentGame;

    private QuestionManager questionManager;

    private TextView question;
    private ImageView shakeImage;
    private TextView shakeMessage;


    ArrayList<AnswerItem> answerList;
    ArrayList<String> answers;

    public GameFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StateListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);

        soundPlayer = new SoundPlayer(getContext());

        question = view.findViewById(R.id.question);
        shakeImage = view.findViewById(R.id.shake);
        recyclerView = view.findViewById(R.id.answers);
        shakeMessage = view.findViewById(R.id.shakeMessage);

        return view;
    }

    // enables sound via being called in start game to check for setting
    public void enableSound(Boolean gameSoundDesire) {
        if (gameSoundDesire) {
            gameSound = Boolean.TRUE;
        } else {
            gameSound = Boolean.FALSE;
        }
    }

    // sets the game and question manager
    public void setGame(Game game, QuestionManager newQuestionManager) {
        currentGame = game;
        questionManager = newQuestionManager;
        newQuestion();
    }

    // calls new question generation and set answers
    public void newQuestion() {
        questionManager.createQuestion();
        setAnswers();
    }

    // sets answers via calling generate answers including the actual answer and shuffles
    public void setAnswers() {
        answerList = new ArrayList<>();
        answers = questionManager.generateAnswers();
        int size = answers.size();
        for(int i = 0; i < size; i++) {
            answerList.add(new AnswerItem(String.valueOf(answers.get(i))));
        }
        Collections.shuffle(answerList);

        showNextQuestion();
    }

    // sets the question to text view
    public void setQuestion(String text) {
        question.setText(text);
    }

    // gets score from the current game
    public int getScore() {
        return currentGame.getScore();
    }

    // shows the next question by building recycler view
    public void showNextQuestion() {
        setQuestion(questionManager.getQuestion());
        buildRecyclerView();
    }

    // gets the selected item from recycler view
    public String getAnswer(int position) {
        String selected = answerList.get(position).getAnswer();
        return selected;
    }

    // initialises and builds recycler view and adds answers
    public void buildRecyclerView() {
        AnswerAdapter recyclerAdapter;
        RecyclerView.LayoutManager recyclerLayoutManager;
        recyclerLayoutManager = new GridLayoutManager(getContext(), answerList.size());

        recyclerAdapter = new AnswerAdapter(answerList);

        recyclerView.setLayoutManager(recyclerLayoutManager);

        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener(position -> {
            String answer = getAnswer(position);
            if (questionManager.check(answer.toString())) {
                currentGame.updateScore(questionManager.check(answer.toString()));
                listener.onUpdate(State.CONTINUE_GAME);
                soundPlayer.playCorrect(gameSound);
            } else {
                soundPlayer.playWrong(gameSound);
                listener.onUpdate(State.GAME_OVER);
            }
        });
    }

    // utilised as end screen for game over sequence
    public void endScreen() {
        recyclerView.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        shakeImage.setVisibility(View.INVISIBLE);
        shakeMessage.setVisibility(View.INVISIBLE);
    }

    // set visibility of game icons
    public void setVisibility(boolean isVisible) {
        if(isVisible) {
            recyclerView.setVisibility(View.VISIBLE);
            question.setVisibility(View.VISIBLE);
            shakeImage.setVisibility(View.INVISIBLE);
            shakeMessage.setVisibility(View.INVISIBLE);
        }
        else {
            recyclerView.setVisibility(View.INVISIBLE);
            question.setVisibility(View.INVISIBLE);
            shakeImage.setVisibility(View.VISIBLE);
            shakeMessage.setVisibility(View.VISIBLE);
        }
    }

    // starts a new game called via shake detector
    public void newGame () {
        listener.onUpdate(State.START_GAME);
    }
}


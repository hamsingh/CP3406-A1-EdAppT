package cp3406.jcu.edu.au.edappt.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

// manages questions creation
public class QuestionManager {

    private String newQuestion;
    private String strGameDifficulty;
    private Difficulty gameDifficulty;
    private Question question;
    private int answer;
    private int firstN;
    private int secondN;
    private ArrayList answers = new ArrayList<String>();

    public QuestionManager(String strGameDifficulty) {
        this.strGameDifficulty = strGameDifficulty;
    }

    // creates question
    public void createQuestion(){
        gameDifficulty = Difficulty.valueOf(strGameDifficulty.toUpperCase());
        generateQuestion(gameDifficulty);
        question = new Question(answer, answers, newQuestion);
    }

    // generate question using two random numbers in range of set difficulty
    public void generateQuestion(Difficulty difficulty) {
        Random rand = new Random();

        firstN = rand.nextInt(difficulty.getDifficulty());
        secondN = rand.nextInt(difficulty.getDifficulty());

        answer = generateAnswer(firstN,secondN);
        answers = generateAnswers();
        newQuestion = String.format(Locale.ENGLISH, "%d x %d", firstN, secondN);
    }


    // generate random answer perfrom checks add actual answer and shuffle
    public ArrayList generateAnswers() {
        answers.clear();
        int number = gameDifficulty.getPossibleAnswers();
        for (int i = 0; i < number; i++) {
            String newAnswer = Integer.toString(getRandomAnswer());
            if (answers.contains(newAnswer)) {
                answers.add(Integer.toString(getRandomAnswer()));
            } else {
                answers.add(newAnswer);
            }
        }
        // if arraylist already has the answer in there from randomly generated answers generate a new answer
        if (answers.contains(getAnswer())) {
            answers.add(Integer.toString(getRandomAnswer()));
        } else {
            answers.add(getAnswer());
        }
        Collections.shuffle(answers); // shuffle

        return answers;
    }

    // generate random answers from 0 to answer + the difficulty
    public int getRandomAnswer() {
        int randomAnswer;
        Random rand = new Random();
        randomAnswer = rand.nextInt(getAnswer() + gameDifficulty.getDifficulty());
        return randomAnswer;
    }

    // calculates actual answer
    public int generateAnswer(int firstN, int secondN) {
        int answer;
        answer = firstN * secondN;
        return answer;
    }

    // gets question
    public String getQuestion() {
        return question.getQuestion();
    }

    // get actual answer
    public int getAnswer() {
        return answer;
    }

    // checks the answer
    public boolean check(String guess) {
        return question.check(guess);
    }
}

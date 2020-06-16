package cp3406.jcu.edu.au.edappt.game;


import java.util.ArrayList;

// store question data and is made for every new question
public class Question {
    private int answer;
    private String question;
    private ArrayList possibleAnswers;

    public Question(int answer, ArrayList possibleAnswers, String question) {
        this.answer = answer;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
    }

    public boolean check(String strGuess) {
        int guess = Integer.parseInt(strGuess);
        return guess==answer;
    }

    public ArrayList getPossibleAnswers() {
        return possibleAnswers;
    }

    public String getQuestion() {
        return question;
    }
}
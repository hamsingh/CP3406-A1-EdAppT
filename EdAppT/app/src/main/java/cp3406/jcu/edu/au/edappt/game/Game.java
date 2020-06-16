package cp3406.jcu.edu.au.edappt.game;

public class Game {
    private int score;

    public Game() {
        score = 0;
    }

    public void updateScore(boolean correct) {
        if(correct) score++;
    }

    public int getScore() {
        return score;
    }

}

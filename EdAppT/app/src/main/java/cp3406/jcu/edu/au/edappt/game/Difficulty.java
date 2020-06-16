package cp3406.jcu.edu.au.edappt.game;

//  difficulty enum for question range and possible answers
public enum Difficulty
{
    EASY (10, 1),
    MEDIUM (12, 3),
    HARD (20, 5),
    ADVANCED (100, 7);

    private int difficulty;
    private int possibleAnswers;

    Difficulty(int difficulty, int possibleAnswers) {
        this.difficulty = difficulty;
        this.possibleAnswers = possibleAnswers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getPossibleAnswers() {
        return possibleAnswers;
    }
}

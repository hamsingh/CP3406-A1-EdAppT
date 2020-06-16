package cp3406.jcu.edu.au.edappt;

public class HighScoreItem {
    private String name;
    private String difficulty;
    private String time;
    private String score;

    public HighScoreItem(String name, String difficulty, String time, String score) {
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }
}

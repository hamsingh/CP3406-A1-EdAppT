package cp3406.jcu.edu.au.edappt;

// this calls creates an item for the recycler view
public class AnswerItem {
    private String answer;

    public AnswerItem(String text) {
        answer = text;
    }

    public String getAnswer() {
        return answer;
    }
}

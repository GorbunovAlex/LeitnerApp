package alexgorbunov.space.leitnercards.model;

import androidx.annotation.NonNull;

public class Flashcard {
    private String question;
    private String answer;
    private int box;

    // Constructor
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.box = 1; // Start in the first box
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    // Method to promote the card to the next box
    public void promote() {
        if (box < 5) { // Assuming there are 5 boxes
            box++;
        }
    }

    // Method to demote the card to the previous box
    public void demote() {
        if (box > 1) {
            box--;
        }
    }

    // Method to reset the card to the first box
    public void reset() {
        box = 1;
    }

    @NonNull
    @Override
    public String toString() {
        return "FlashCard{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", box=" + box +
                '}';
    }
}

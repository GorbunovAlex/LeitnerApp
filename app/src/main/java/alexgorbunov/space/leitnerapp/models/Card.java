package alexgorbunov.space.leitnerapp.models;

public class Card {
    private int id;
    private String question;
    private String answer;
    private CardBox cardBox;

    public Card(int id, String question, String answer, CardBox cardBox) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.cardBox = cardBox;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public CardBox getCardBox() {
        return cardBox;
    }


}

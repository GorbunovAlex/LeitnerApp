package alexgorbunov.space.leitnerapp.models;

public class Card {
    private final int id;
    private final String question;
    private final String answer;
    private CardBox cardBox;

    public Card(int id, String question, String answer, CardBox cardBox) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.cardBox = cardBox;
    }

    public void setCardBox(CardBox cardBox) {
        this.cardBox = cardBox;
    }

    public CardBox getCardBox() {
        return cardBox;
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
}

package alexgorbunov.space.leitnerapp.view_model;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.repositories.LocalRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;

public class CardsViewModel extends ViewModel {
    private final LocalRepository repository;
    private ArrayList<Card> cardList = new ArrayList<>();

    public CardsViewModel(Context context) {
        this.repository = new LocalRepository(context);
        this.cardList = this.repository.getCards();
    }

    public ArrayList<Card> getCards() {
        return this.cardList;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cardList = cards;
        this.repository.writeCards(cards);
    }

    public void addCard(Card card) {
        this.cardList.add(card);
        this.repository.writeCards(this.cardList);
    }
}

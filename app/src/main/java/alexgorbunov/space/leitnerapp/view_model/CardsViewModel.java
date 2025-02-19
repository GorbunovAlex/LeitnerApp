package alexgorbunov.space.leitnerapp.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.repositories.LocalRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;

import javax.inject.Inject;

@HiltViewModel
public class CardsViewModel extends ViewModel {
    private final LocalRepository repository;
    private final MutableLiveData<ArrayList<Card>> cardList = new MutableLiveData<>();

    @Inject
    public CardsViewModel(Context context) {
        this.repository = new LocalRepository(context);
        this.cardList.setValue(this.repository.getCards());
    }

    public LiveData<ArrayList<Card>> getCards() {
        return this.cardList;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cardList.setValue(cards);
        this.repository.writeCards(cards);
    }

    public void addCard(Card card) {
        ArrayList<Card> currentList = this.cardList.getValue();
        if (currentList != null) {
            currentList.add(card);
            this.cardList.setValue(currentList);
            this.repository.writeCards(currentList);
        }
    }
}

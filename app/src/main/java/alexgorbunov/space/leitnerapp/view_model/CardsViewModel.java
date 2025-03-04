package alexgorbunov.space.leitnerapp.view_model;

import alexgorbunov.space.leitnerapp.repositories.CardsRepository;
import alexgorbunov.space.leitnerapp.repositories.RepositoryCallback;
import android.content.Context;

import android.os.Looper;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Objects;

import alexgorbunov.space.leitnerapp.models.Card;
import dagger.hilt.android.lifecycle.HiltViewModel;

import javax.inject.Inject;

@HiltViewModel
public class CardsViewModel extends ViewModel {
    private final CardsRepository repository;
    private final MutableLiveData<ArrayList<Card>> cardList = new MutableLiveData<>();

    @Inject
    public CardsViewModel(Context context) {
        this.repository = new CardsRepository(context);
        this.getCardsFromDatabase();
    }

    public void getCardsFromDatabase() {
        this.repository.getCards(new RepositoryCallback<ArrayList<Card>>() {
            @Override
            public void onComplete(ArrayList<Card> result) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    cardList.setValue(Objects.requireNonNullElseGet(result, ArrayList::new));
                } else {
                    new Handler(Looper.getMainLooper()).post(() ->
                            cardList.setValue(Objects.requireNonNullElseGet(result, ArrayList::new))
                    );
                }
            }
        });
    }

    public LiveData<ArrayList<Card>> getCardList() {
        return this.cardList;
    }

    public void addCard(Card card) {
        ArrayList<Card> currentList = this.cardList.getValue();
        if (currentList != null) {
            currentList.add(card);
            this.cardList.setValue(currentList);
            this.repository.writeCards(currentList, new RepositoryCallback<Boolean>() {
                @Override
                public void onComplete(Boolean result) {
                    if (!result) {
                        currentList.remove(card);
                        cardList.setValue(currentList);
                    }
                }
            });
        }
    }

    public void deleteCard(int cardId) {
        ArrayList<Card> currentList = this.cardList.getValue();
        if (currentList != null) {
            currentList.removeIf(card -> card.getId() == cardId);
            this.cardList.setValue(currentList);
            this.repository.deleteCard(cardId, new RepositoryCallback<Boolean>() {
                @Override
                public void onComplete(Boolean result) {
                    if (result) {
                        getCardsFromDatabase();
                    }
                }
            });
        }
    }
}

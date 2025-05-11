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
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import alexgorbunov.space.leitnerapp.models.Card;
import dagger.hilt.android.lifecycle.HiltViewModel;

import javax.inject.Inject;

@HiltViewModel
public class CardsViewModel extends ViewModel {
    private final CardsRepository repository;
    private final MutableLiveData<ArrayList<Card>> cardList = new MutableLiveData<>();
    private final Logger logger;

    @Inject
    public CardsViewModel(Context context) {
        this.repository = new CardsRepository(context);
        this.getCardsFromDatabase();
        logger = Logger.getLogger(context.getPackageName());
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
            this.repository.addCard(card, new RepositoryCallback<Boolean>() {
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

    public void updateCard(Card card) {
        ArrayList<Card> currentList = this.cardList.getValue();
        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                if (currentList.get(i).getId() == card.getId()) {
                    currentList.set(i, card);
                    break;
                }
            }
            this.cardList.setValue(currentList);
            this.repository.updateCard(card, new RepositoryCallback<Boolean>() {
                @Override
                public void onComplete(Boolean result) {
                    if (result) {
                        getCardsFromDatabase();
                    } else {
                        logger.log(new LogRecord(Level.WARNING, "Failed to update card"));
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

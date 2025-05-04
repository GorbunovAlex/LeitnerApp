package alexgorbunov.space.leitnerapp.repositories;

import alexgorbunov.space.leitnerapp.datasources.DatabaseDatasource;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.models.dao.CardDao;
import android.annotation.SuppressLint;
import android.content.Context;
import androidx.room.Room;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CardsRepository {
    private final CardDao cardDao;

    public CardsRepository(Context context) {
        DatabaseDatasource database = Room.databaseBuilder(context,
                DatabaseDatasource.class, "leitner_db").build();
        this.cardDao = database.cardDao();
    }

    public void getCards(final RepositoryCallback<ArrayList<Card>> callback) {
        cardDao.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Card>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        // Handle subscription
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Card> cards) {
                        callback.onComplete(new ArrayList<>(cards));
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        callback.onComplete(null);
                    }
                });
    }

    public void getCardById(int cardId, final RepositoryCallback<Card> callback) {
        cardDao.getById(cardId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Card>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        // Handle subscription
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Card card) {
                        callback.onComplete(card);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        callback.onComplete(null);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void addCard(Card card, final RepositoryCallback<Boolean> callback) {
        cardDao.insert(card)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    callback.onComplete(true);
                }, throwable -> {
                    callback.onComplete(false);
                });
    }

    @SuppressLint("CheckResult")
    public void updateCard(Card card, final RepositoryCallback<Boolean> callback) {
        cardDao.update(card)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    callback.onComplete(true);
                }, throwable -> {
                    callback.onComplete(false);
                });
    }

    @SuppressLint("CheckResult")
    public void deleteCard(int cardId, final RepositoryCallback<Boolean> callback) {
        cardDao.getById(cardId)
                .flatMapCompletable(cardDao::delete)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    callback.onComplete(true);
                }, throwable -> {
                    callback.onComplete(false);
                });
    }
}

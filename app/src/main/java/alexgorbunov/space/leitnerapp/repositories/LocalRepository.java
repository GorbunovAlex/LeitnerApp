package alexgorbunov.space.leitnerapp.repositories;

import android.content.Context;

import java.util.ArrayList;

import alexgorbunov.space.leitnerapp.datasources.LocalDatasource;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.models.CardBox;

public class LocalRepository {
    private final LocalDatasource dataSource;
    public LocalRepository(Context context) {
        this.dataSource = new LocalDatasource(context);
    }

    public ArrayList<Card> getCards() {
        return this.dataSource.getCards();
    };

    public void writeCards(ArrayList<Card> cards) {
        this.dataSource.writeCards(cards);
    };

    public ArrayList<CardBox> getCardBoxes() {
        return this.dataSource.readCardBoxes();
    };
}

package alexgorbunov.space.leitnerapp.models.dao;

import alexgorbunov.space.leitnerapp.models.Card;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM cards")
    Single<List<Card>> getAll();

    @Query("SELECT * FROM cards WHERE id = :id")
    Single<Card> getById(int id);

    @Insert
    Completable insert(Card card);

    @Update
    Completable update(Card card);

    @Insert
    Completable insertAll(List<Card> cards);

    @Delete
    Completable delete(Card card);
}

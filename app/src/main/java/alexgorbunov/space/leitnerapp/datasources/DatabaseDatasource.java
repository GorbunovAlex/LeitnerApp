package alexgorbunov.space.leitnerapp.datasources;

import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.models.dao.CardDao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Card.class}, version = 1)
public abstract class DatabaseDatasource extends RoomDatabase {
    public abstract CardDao cardDao();
}
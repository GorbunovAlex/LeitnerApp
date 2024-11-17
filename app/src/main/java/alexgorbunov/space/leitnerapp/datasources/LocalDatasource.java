package alexgorbunov.space.leitnerapp.datasources;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import alexgorbunov.space.leitnerapp.Consts;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.models.CardBox;

public class LocalDatasource {
    private final Context context;
    private final File cards_store;

    public LocalDatasource(Context context) {
        this.context = context;
        this.cards_store = new File(context.getFilesDir(), Consts.CARDS_STORE);
    }

    public ArrayList<Card> getCards() {
        try {
            FileInputStream fis = this.context.openFileInput(Consts.CARDS_STORE);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                Log.d(Consts.LOCAL_DATASOURCE_TAG, Objects.requireNonNull(e.getMessage()));
                return new ArrayList<>();
            }
            return Card.readCardsFromJson(stringBuilder.toString());
        } catch (IOException e) {
            Log.d(Consts.LOCAL_DATASOURCE_TAG, Objects.requireNonNull(e.getMessage()));
            return new ArrayList<>();
        }
    }

    public void writeCards(ArrayList<Card> cards) {
        try {
            String json = Card.writeCardsToJson(cards);
            try (FileOutputStream fos = this.context.openFileOutput(Consts.CARDS_STORE, Context.MODE_PRIVATE)) {
                fos.write(json.getBytes());
            }
        } catch (IOException e) {
            Log.d(Consts.LOCAL_DATASOURCE_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    public ArrayList<CardBox> readCardBoxes() {
        ArrayList<CardBox> cardBoxes;
        return new ArrayList<>();
    }
}

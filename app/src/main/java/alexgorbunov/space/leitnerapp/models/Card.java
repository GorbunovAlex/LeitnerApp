package alexgorbunov.space.leitnerapp.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import alexgorbunov.space.leitnerapp.Consts;

@Entity(tableName = "cards")
public class Card implements Serializable {
    @PrimaryKey
    private final int id;
    @ColumnInfo(name = "question")
    private final String question;
    @ColumnInfo(name = "answer")
    private final String answer;
    @ColumnInfo(name = "card_box_id", defaultValue = "1")
    private int cardBoxId;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Card(int id, String question, String answer, int cardBoxId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.cardBoxId = cardBoxId;
    }

    public void setCardBox(int cardBoxId) {
        this.cardBoxId = cardBoxId;
    }

    public int getCardBoxId() {
        return cardBoxId;
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

    private static Card fromJson(LinkedHashMap<String, Object> map) {
        Integer cardId = (Integer) map.get("id");
        Integer cardBoxId = (Integer) map.get("cardBox");
        Log.d(Consts.CARD_CLASS_TAG, "Card id: " + cardId + ", cardBoxId: " + cardBoxId);
        if (cardId == null || cardBoxId == null) {
            Log.e(Consts.CARD_CLASS_TAG, "Card id or cardBoxId is null");
            return null;
        }
        return new Card(
                cardId,
                (String) map.get("question"),
                (String) map.get("answer"),
                cardBoxId
        );
    }

    public static ArrayList<Card> readCardsFromJson(String json) {
        try {
            List<LinkedHashMap<String, Object>> raw_cards = objectMapper.readValue(json, new TypeReference<List<LinkedHashMap<String, Object>>>() {});
            Log.d(Consts.LOCAL_DATASOURCE_TAG, raw_cards.toString());
            ArrayList<Card> cards = raw_cards.stream().map(card -> {
                try {
                    return fromJson(card);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toCollection(ArrayList::new));
            Log.d(Consts.LOCAL_DATASOURCE_TAG, cards.toString());
            return cards;
        } catch (IOException e) {
            Log.d(Consts.LOCAL_DATASOURCE_TAG, Objects.requireNonNull(e.getMessage()));
            return new ArrayList<>();
        }
    }

    public static String writeCardsToJson(ArrayList<Card> cards) {
        try {
            return objectMapper.writeValueAsString(cards);
        } catch (IOException e) {
            Log.d(Consts.LOCAL_DATASOURCE_TAG, Objects.requireNonNull(e.getMessage()));
            return "";
        }
    }
}

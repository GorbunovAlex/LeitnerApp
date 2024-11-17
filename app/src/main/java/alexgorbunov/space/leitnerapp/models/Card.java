package alexgorbunov.space.leitnerapp.models;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import alexgorbunov.space.leitnerapp.Consts;

public class Card {
    private final int id;
    private final String question;
    private final String answer;
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

    public int getCardBox() {
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

    public static ArrayList<Card> readCardsFromJson(String json) {
        try {
            List<Card> cards = Collections.emptyList();
            List<String> raw_cards = objectMapper.readValue(json, List.class);
            raw_cards.stream().map(card -> {
                try {
                    return objectMapper.readValue(card, Card.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            Log.d(Consts.LOCAL_DATASOURCE_TAG, cards.toString());
            return new ArrayList<>();
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

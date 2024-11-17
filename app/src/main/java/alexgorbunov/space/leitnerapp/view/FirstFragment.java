package alexgorbunov.space.leitnerapp.view;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import alexgorbunov.space.leitnerapp.databinding.FragmentFirstBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;

public class FirstFragment extends Fragment {
    Context context;
    CardsViewModel cardsViewModel;
    List<Card> cards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireActivity().getApplicationContext();
        this.cardsViewModel = new CardsViewModel(this.context);
    }

    private FragmentFirstBinding binding;

    private void getCards() {
        this.cards = cardsViewModel.getCards();
        binding.textviewFirst.setText(this.cards.toString());
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText cardQuestion = binding.cardQuestion;
        EditText cardAnswer = binding.cardAnswer;

        binding.addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardQuestionText = cardQuestion.getText().toString();
                String cardAnswerText = cardAnswer.getText().toString();
                int cardId = new UUID(cardQuestionText.length(), cardAnswerText.length()).hashCode();
                // HARCODED CARDBOXID
                // TODO: make it dynamic
                Card card = new Card(cardId, cardQuestionText, cardAnswerText, 1);
                cardsViewModel.addCard(card);
                getCards();
            }
        });

        this.getCards();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
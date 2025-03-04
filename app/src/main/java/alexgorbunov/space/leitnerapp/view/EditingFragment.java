package alexgorbunov.space.leitnerapp.view;

import java.util.UUID;

import alexgorbunov.space.leitnerapp.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import alexgorbunov.space.leitnerapp.databinding.FragmentEditingBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditingFragment extends Fragment {
    Context context;
    CardsViewModel cardsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireActivity().getApplicationContext();
        this.cardsViewModel = new ViewModelProvider(this).get(CardsViewModel.class);
    }

    private FragmentEditingBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEditingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText cardQuestion = binding.cardQuestion;
        EditText cardAnswer = binding.cardAnswer;

        NavController navController = NavHostFragment.findNavController(this);

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
                navController.navigate(R.id.CardsFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
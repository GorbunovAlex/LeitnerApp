package alexgorbunov.space.leitnerapp.view;

import alexgorbunov.space.leitnerapp.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.List;

import alexgorbunov.space.leitnerapp.databinding.FragmentCardBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;
import androidx.navigation.Navigation;

public class CardFragment extends Fragment {
    Context context;
    CardsViewModel cardsViewModel;
    List<Card> cards;

    private boolean isFlipped = false;
    private TextView frontText, backText;
    private CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireActivity().getApplicationContext();
        this.cardsViewModel = new CardsViewModel(this.context);
    }

    private FragmentCardBinding binding;

    private void getCards() {
        cardsViewModel.getCards().observe(getViewLifecycleOwner(), cards -> {
            this.cards = cards;
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.getCards();

        cardView = view.findViewById(R.id.cardView);
        frontText = view.findViewById(R.id.cardFrontText);
        backText = view.findViewById(R.id.cardBackText);

        // Flip the card when clicked
        cardView.setOnClickListener(v -> flipCard());
    }

    private void flipCard() {
        isFlipped = !isFlipped;
        float rotationY = isFlipped ? 180f : 0f;
        cardView.animate().rotationY(rotationY).setDuration(300).withEndAction(() -> {
            if (isFlipped) {
                frontText.setVisibility(View.GONE);
                backText.setVisibility(View.VISIBLE);
            } else {
                frontText.setVisibility(View.VISIBLE);
                backText.setVisibility(View.GONE);
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

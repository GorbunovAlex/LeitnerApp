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
import java.util.logging.Logger;

import alexgorbunov.space.leitnerapp.databinding.FragmentCardBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;
import androidx.navigation.Navigation;

public class CardFragment extends Fragment {
    Context context;
    Logger logger;
    Card card;

    private boolean isFlipped = false;
    private TextView frontText, backText;
    private CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireActivity().getApplicationContext();
        logger = Logger.getLogger(context.getPackageName());
    }

    private FragmentCardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        this.card = (Card) getArguments().getSerializable("card");

        cardView = view.findViewById(R.id.cardView);
        frontText = view.findViewById(R.id.cardFrontText);
        backText = view.findViewById(R.id.cardBackText);

        frontText.setText(this.card.getQuestion());
        backText.setText(this.card.getAnswer());

        // Flip the card when clicked
        cardView.setOnClickListener(v -> flipCard());
    }

    private void flipCard() {
        isFlipped = !isFlipped;
        if (isFlipped) {
            cardView.animate().rotationY(90f).setDuration(150).withEndAction(() -> {
                frontText.setVisibility(View.GONE);
                backText.setVisibility(View.VISIBLE);
                cardView.setRotationY(-90f);
                cardView.animate().rotationY(0f).setDuration(150).start();
            }).start();
        } else {
            cardView.animate().rotationY(90f).setDuration(150).withEndAction(() -> {
                backText.setVisibility(View.GONE);
                frontText.setVisibility(View.VISIBLE);
                cardView.setRotationY(-90f);
                cardView.animate().rotationY(0f).setDuration(150).start();
            }).start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

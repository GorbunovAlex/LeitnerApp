package alexgorbunov.space.leitnerapp.view;

import alexgorbunov.space.leitnerapp.R;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;
import android.content.Context;
import android.os.Bundle;
import android.view.*;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.logging.Logger;

import alexgorbunov.space.leitnerapp.databinding.FragmentCardBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CardFragment extends Fragment {
    Context context;
    Logger logger;
    Card card;
    CardsViewModel cardsViewModel;

    private boolean isFlipped = false;
    private TextView frontText, backText;
    private CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cardsViewModel = new ViewModelProvider(this).get(CardsViewModel.class);

        setHasOptionsMenu(true);
        this.context = requireActivity().getApplicationContext();
        logger = Logger.getLogger(context.getPackageName());
    }

    private FragmentCardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        if (id == R.id.edit_card) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("card", card);
            navController.navigate(R.id.EditingFragment, bundle);
            return true;
        } else if (id == R.id.delete_card) {
            this.cardsViewModel.deleteCard(card.getId());
            navController.navigate(R.id.CardsFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        cardView.setOnClickListener(v -> flipCard());
    }

    private void flipCard() {
        isFlipped = !isFlipped;

        final int DURATION = 200;
        final float SCALE_DOWN = 0.8f;

        if (isFlipped) {
            cardView.animate()
                    .scaleX(SCALE_DOWN)
                    .scaleY(SCALE_DOWN)
                    .setDuration(DURATION / 2)
                    .withEndAction(() -> {
                        frontText.setVisibility(View.GONE);
                        backText.setVisibility(View.VISIBLE);

                        cardView.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(DURATION / 2)
                                .start();
                    }).start();
        } else {
            cardView.animate()
                    .scaleX(SCALE_DOWN)
                    .scaleY(SCALE_DOWN)
                    .setDuration(DURATION / 2)
                    .withEndAction(() -> {
                        backText.setVisibility(View.GONE);
                        frontText.setVisibility(View.VISIBLE);

                        cardView.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(DURATION / 2)
                                .start();
                    }).start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

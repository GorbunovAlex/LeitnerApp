package alexgorbunov.space.leitnerapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import alexgorbunov.space.leitnerapp.databinding.FragmentCardBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;

public class CardFragment extends Fragment {
    Context context;
    CardsViewModel cardsViewModel;
    List<Card> cards;

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
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.getCards();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

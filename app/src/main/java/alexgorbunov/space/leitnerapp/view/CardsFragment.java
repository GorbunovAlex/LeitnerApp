package alexgorbunov.space.leitnerapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.stream.Collectors;

import alexgorbunov.space.leitnerapp.R;
import alexgorbunov.space.leitnerapp.databinding.FragmentCardsBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;

public class CardsFragment extends Fragment {
    Context context;
    CardsViewModel cardsViewModel;
    List<Card> cards;
    ListView cardsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireActivity().getApplicationContext();
        this.cardsViewModel = new CardsViewModel(this.context);
    }

    private FragmentCardsBinding binding;

    private void getCards() {
        this.cards = cardsViewModel.getCards();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCardsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getCards();

        cardsList = cardsList.findViewById(R.id.cards_list);
        ArrayAdapter<String> arr;

        List<String> questions = cards.stream().map(Card::getQuestion).collect(Collectors.toList());

        arr = new ArrayAdapter<String>(context, R.layout.fragment_cards, questions);
        cardsList.setAdapter(arr);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

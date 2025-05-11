package alexgorbunov.space.leitnerapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import alexgorbunov.space.leitnerapp.R;
import alexgorbunov.space.leitnerapp.databinding.FragmentCardsBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CardsFragment extends Fragment {
    Context context;
    Logger logger;

    CardsViewModel cardsViewModel;

    ListView cardsList;
    private ArrayList<Card> currentCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireActivity().getApplicationContext();
        this.cardsViewModel = new ViewModelProvider(this).get(CardsViewModel.class);
        logger = Logger.getLogger(context.getPackageName());
    }

    private FragmentCardsBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCardsBinding.inflate(inflater, container, false);
        ListView list = (ListView) binding.getRoot().findViewById(R.id.cards_list);
        TextView emptyText = (TextView) binding.getRoot().findViewById(R.id.emptyElement);
        list.setEmptyView(emptyText);
        return binding.getRoot();
    }

    private void getCards() {
        cardsViewModel.getCardList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Card>>() {
            @Override
            public void onChanged(ArrayList<Card> cards) {
                currentCards = cards;
                logger.info(Integer.toString(cards.size()));
                List<String> questions = cards.stream().map(Card::getQuestion).collect(Collectors.toList());
                ArrayAdapter<String> arr = new ArrayAdapter<>(context, R.layout.card_list_item, questions);
                cardsList.setAdapter(arr);
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardsList = view.findViewById(R.id.cards_list);

        // Set up click listener
        cardsList.setOnItemClickListener((parent, view1, position, id) -> {
            Card selectedCard = currentCards.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("card", selectedCard);
            Navigation.findNavController(view)
                    .navigate(R.id.action_CardsFragment_to_CardFragment, bundle);
        });

        getCards();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

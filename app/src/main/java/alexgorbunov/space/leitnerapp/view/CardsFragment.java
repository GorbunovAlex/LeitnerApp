package alexgorbunov.space.leitnerapp.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import alexgorbunov.space.leitnerapp.R;
import alexgorbunov.space.leitnerapp.databinding.FragmentCardsBinding;
import alexgorbunov.space.leitnerapp.models.Card;
import alexgorbunov.space.leitnerapp.view_model.CardsViewModel;

public class CardsFragment extends Fragment {
    Context context;
    Logger logger;

    @Inject
    CardsViewModel cardsViewModel;
    ListView cardsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = requireActivity().getApplicationContext();
        logger = Logger.getLogger(context.getPackageName());
    }

    private FragmentCardsBinding binding;

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

        cardsList = view.findViewById(R.id.cards_list);
        ArrayAdapter<String> arr;

        List<Card> cards = cardsViewModel.getCards();

        logger.info(Integer.toString(cards.size()));

        List<String> questions = cards.stream().map(Card::getQuestion).collect(Collectors.toList());

        arr = new ArrayAdapter<String>(context, R.layout.card_list_item, questions);
        cardsList.setAdapter(arr);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

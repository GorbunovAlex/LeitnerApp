package alexgorbunov.space.leitnerapp.view_model;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.List;

import alexgorbunov.space.leitnerapp.models.CardBox;
import alexgorbunov.space.leitnerapp.repositories.LocalRepository;

public class CardBoxesViewModel extends ViewModel {
    private List<CardBox> cardBoxList = List.of();

    public CardBoxesViewModel(Context context) {
        LocalRepository repository = new LocalRepository(context);
        this.cardBoxList = repository.getCardBoxes();
    }

    public List<CardBox> getCardBoxes() {
        return this.cardBoxList;
    }
}

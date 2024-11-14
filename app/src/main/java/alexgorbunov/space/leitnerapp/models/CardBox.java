package alexgorbunov.space.leitnerapp.models;

public class CardBox {
    private int id;
    private String name;

    public CardBox(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

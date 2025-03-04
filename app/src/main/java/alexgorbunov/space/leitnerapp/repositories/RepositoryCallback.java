package alexgorbunov.space.leitnerapp.repositories;

public interface RepositoryCallback<T> {
    void onComplete(T result);
}

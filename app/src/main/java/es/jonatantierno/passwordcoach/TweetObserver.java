package es.jonatantierno.passwordcoach;

public interface TweetObserver {
    void onNext(String text);

    void onCompleted();

    void onError(Exception e);
}

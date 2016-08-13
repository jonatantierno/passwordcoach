package es.jonatantierno.passwordcoach.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import rx.Observable;

import static android.content.Context.MODE_PRIVATE;
import static java.util.Collections.EMPTY_SET;

public class PersistentStringSetObservable {
    public static final String PERSISTENT_OBSERVABLE = "PERSISTENT_OBSERVABLE";
    private final Context context;

    public PersistentStringSetObservable(Context context) {
        this.context = context;
    }

    public Observable<String> save(Observable<String> source) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PERSISTENT_OBSERVABLE, MODE_PRIVATE).edit();

        Set<String> contents = new HashSet<>();

        return source.doOnNext(s -> contents.add(s))
                .doOnCompleted(() -> editor.putStringSet(PERSISTENT_OBSERVABLE, contents).commit()
        );
    }

    public Observable<String> load() {
        SharedPreferences storage = context.getSharedPreferences(PERSISTENT_OBSERVABLE, MODE_PRIVATE);

        return Observable.from(storage.getStringSet(PERSISTENT_OBSERVABLE, EMPTY_SET));
    }

    public boolean empty() {
        SharedPreferences storage = context.getSharedPreferences(PERSISTENT_OBSERVABLE, MODE_PRIVATE);
        return !storage.contains(PERSISTENT_OBSERVABLE);
    }

    public void clear() {
        SharedPreferences storage = context.getSharedPreferences(PERSISTENT_OBSERVABLE, MODE_PRIVATE);
        storage.edit().clear().commit();
    }
}

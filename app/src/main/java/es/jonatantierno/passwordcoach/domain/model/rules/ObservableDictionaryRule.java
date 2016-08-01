package es.jonatantierno.passwordcoach.domain.model.rules;

import java.util.Iterator;

import rx.Observable;

/**
 * A dictionary in the form of an observable of strings.
 */
public class ObservableDictionaryRule implements Rule {
    private final Observable<String> dictionary;

    public ObservableDictionaryRule(Observable<String> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public Result analyze(final String password) {
        Iterator<String> coincidences = dictionary.doOnError(
                e -> {
                    throw new RuntimeException(e);
                })
                .filter(s -> s.equals(password))
                .toBlocking().getIterator();

        if (coincidences.hasNext()) {
            return new WeakPasswordResult(ResultCode.IN_DICTIONARY);
        } else {
            return new StrongPasswordResult();
        }
    }
}

package es.jonatantierno.passwordcoach.domain.model.rules;

import java.util.Iterator;

import rx.Observable;

/**
 * A dictionary in the form of an observable of strings.
 */
public class ObservableDictionaryRule implements Rule {
    private final Observable<String> dictionary;
    private final WordCheck check;

    public ObservableDictionaryRule(Observable<String> dictionary) {
        this(dictionary, new WordCheck());
    }

    public ObservableDictionaryRule(Observable<String> dictionary, WordCheck check) {
        this.dictionary = dictionary;
        this.check = check;
    }

    @Override
    public Result analyze(final String password) {
        Iterator<String> coincidences = dictionary.doOnError(
                e -> {
                    throw new RuntimeException(e);
                })
                .takeFirst(word -> check.analyze(word,password) != ResultCode.STRONG)
                .toBlocking().getIterator();

        if (coincidences.hasNext()) {
            return new WeakPasswordResult(ResultCode.IN_PERSONAL_ATTACK_DICTIONARY);
        } else {
            return new StrongPasswordResult();
        }
    }
}

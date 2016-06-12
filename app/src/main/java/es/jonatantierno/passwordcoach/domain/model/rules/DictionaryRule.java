package es.jonatantierno.passwordcoach.domain.model.rules;

import es.jonatantierno.passwordcoach.domain.model.dictionary.DictionaryIterator;
import es.jonatantierno.passwordcoach.domain.model.dictionary.LineStream;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class DictionaryRule implements Rule {
    private final Iterator<String> dictionary;

    public DictionaryRule(Iterator<String> dictionary) {

        this.dictionary = dictionary;
    }

    public DictionaryRule(InputStream inputStream) {
        this(
                new DictionaryIterator(
                        new LineStream(
                                new BufferedReader(
                                        new InputStreamReader(inputStream)
                                )
                        )
                )
        );
    }

    @Override
    public Result analyze(String password) {
        while (dictionary.hasNext()) {
            if (dictionary.next().equals(password)) {
                return new WeakPasswordResult(ResultCode.IN_DICTIONARY);
            }
        }
        return new StrongPasswordResult();
    }
}

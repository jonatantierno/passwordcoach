package es.jonatantierno.passwordcoach.domain.model.rules;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import es.jonatantierno.passwordcoach.domain.model.dictionary.DictionaryIterator;
import es.jonatantierno.passwordcoach.domain.model.dictionary.LineStream;

public class DictionaryRule implements Rule {
    public static final int MIN_DICTIONARY_WORD_LENGTH = 3;
    private final Iterator<String> dictionary;
    private boolean used = false;

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
        if (used) throw new IllegalStateException("DictionaryRule can only be used once.");
        used = true;

        while (dictionary.hasNext()) {
            String next = dictionary.next();
            if(next.length() > MIN_DICTIONARY_WORD_LENGTH && password.contains(next)) {
                if (password.equals(next)) {
                    return new WeakPasswordResult(ResultCode.IN_DICTIONARY);
                }
                return new WeakPasswordResult(ResultCode.CONTAINS_WORD_IN_DICTIONARY);
            }
        }
        return new StrongPasswordResult();
    }
}

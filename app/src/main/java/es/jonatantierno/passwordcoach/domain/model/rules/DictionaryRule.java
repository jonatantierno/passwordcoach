package es.jonatantierno.passwordcoach.domain.model.rules;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import es.jonatantierno.passwordcoach.domain.model.dictionary.DictionaryIterator;
import es.jonatantierno.passwordcoach.domain.model.dictionary.LineStream;

public class DictionaryRule implements Rule {
    private final Iterator<String> dictionary;
    private boolean used = false;
    private final WordCheck check;

    public DictionaryRule(Iterator<String> dictionary, WordCheck wordCheck) {
        this.dictionary = dictionary;
        this.check = wordCheck;
    }

    public DictionaryRule(Iterator<String> dictionary) {
        this(dictionary,new WordCheck());
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
            ResultCode resultCode = check.analyze(dictionary.next(), password);

            if (resultCode != ResultCode.STRONG){
                return new WeakPasswordResult(resultCode);
            }
        }
        return new StrongPasswordResult();
    }
}

package com.example.jonatan.passwordcoach.rules;

import java.util.Iterator;

public class DictionaryRule implements Rule{
    private final Iterator<String> dictionary;

    public DictionaryRule(Iterator<String> dictionary) {

        this.dictionary = dictionary;
    }

    @Override
    public Result analyze(String password) {
        while(dictionary.hasNext()) {
            if(dictionary.next().equals(password)) {
                return new WeakPasswordResult();
            }
        }
        return new StrongPasswordResult();
    }
}

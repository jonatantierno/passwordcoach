package es.jonatantierno.passwordcoach.domain.model.dictionary;

import java.util.Iterator;

public class DictionaryIterator implements Iterator<String>{

    private final LineStream stream;
    private String nextLine = null;

    public DictionaryIterator(LineStream stream) {
        this.stream = stream;
    }

    @Override
    public boolean hasNext() {
        nextLine = stream.readLine();
        if (nextLine == null) {
            stream.close();
            return false;
        }
        return true;
    }

    @Override
    public String next() {
        return nextLine;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}


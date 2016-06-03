package com.example.jonatan.passwordcoach.dictionary;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

public class LineStream {
    private final BufferedReader reader;

    public LineStream(BufferedReader reader) {
        this.reader = reader;
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            Log.e("DictionaryIterator", e.toString());
            return null;
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            Log.e("DictionaryIterator", e.toString());
        }
    }
}

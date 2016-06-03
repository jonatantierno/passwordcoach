package com.example.jonatan.passwordcoach.rules;

import android.content.res.Resources;

import com.example.jonatan.passwordcoach.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryRuleTest {

    @Test
    public void wordInDictionary(){
        String password = "password";

        Result result = new DictionaryRule(Arrays.asList(password).iterator()).analyze(password);

        assertThat(result.passwordIsStrong(), is(false));
    }

    @Test
    public void wordNotInDictionary(){
        Result result = new DictionaryRule(Arrays.asList("word").iterator()).analyze("another word");

        assertThat(result.passwordIsStrong(), is(true));
    }

    @Test
    public void emptyDictionary(){
        Result result = new DictionaryRule(Collections.EMPTY_LIST.iterator()).analyze("foo");

        assertThat(result.passwordIsStrong(), is(true));
    }
}
package com.example.jonatan.passwordcoach;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jonatan.passwordcoach.dictionary.DictionaryIterator;
import com.example.jonatan.passwordcoach.dictionary.LineStream;
import com.example.jonatan.passwordcoach.rules.DictionaryRule;
import com.example.jonatan.passwordcoach.rules.EmptyPasswordRule;
import com.example.jonatan.passwordcoach.rules.Result;
import com.example.jonatan.passwordcoach.rules.SetOfRules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements Gui {

    private EditText password;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = (EditText) findViewById(R.id.passwordEditText);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    hideKeyboard();

                    new Analysis(
                            MainActivity.this,
                            new SetOfRules(
                                    Arrays.asList(
                                            new EmptyPasswordRule(),
                                            new DictionaryRule(
                                                    new DictionaryIterator(
                                                            new LineStream(
                                                                    new BufferedReader(
                                                                            new InputStreamReader(
                                                                                    MainActivity.this.getResources().openRawResource(R.raw.spanish_words)
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                            // Prepend season
                                            // Append season
                                            // Prepend hello season
                                            // Prepend years
                                            // Append years
                                            // Append 4 nums
                                            // Append 5 nums
                                            // Append 6 nums
                                            // Append Special + 6 nums
                                            // Append 1234
                                            // Append month day
                                            // Append month current day
                                            // Prepend days week
                                            // Append specials
                                            // ...
                                    )
                            )
                    ).start(password.getText().toString());

                    return true;
                } else {
                    return false;
                }
            }
        });

        result = (TextView) findViewById(R.id.resultTextView);

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
    }

    @Override
    public void show(Result analysisResult) {
        if (analysisResult.passwordIsStrong()) {
            result.setText(R.string.well_done);
        } else {
            result.setText(R.string.you_can_do_better);
        }
    }
}

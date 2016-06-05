package com.example.jonatan.passwordcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jonatan.passwordcoach.domain.model.Analysis;
import com.example.jonatan.passwordcoach.domain.model.rules.DictionaryRule;
import com.example.jonatan.passwordcoach.domain.model.rules.Result;
import com.example.jonatan.passwordcoach.domain.model.rules.ResultCode;
import com.example.jonatan.passwordcoach.domain.model.rules.SetOfRules;
import com.example.jonatan.passwordcoach.domain.model.rules.ShortPasswordRule;
import com.example.jonatan.passwordcoach.domain.ports.Gui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Gui {

    public static final int MIN_LENGTH = 6;
    private EditText password;
    private TextView result;
    private Map<ResultCode, Integer> codeToStringId = buildCodeToStringId();

    private Map<ResultCode,Integer> buildCodeToStringId() {
        HashMap<ResultCode, Integer> map = new HashMap<>();
        map.put(ResultCode.TOO_SHORT, R.string.password_is_too_short);
        map.put(ResultCode.IN_DICTIONARY, R.string.password_is_in_dictionary);
        map.put(ResultCode.WEAK, R.string.password_is_weak);
        map.put(ResultCode.STRONG, R.string.password_is_strong);
        return map;
    }

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
                                            new ShortPasswordRule(MIN_LENGTH),
                                            // No mays, no numbers, no special chars
                                            new DictionaryRule(
                                                    MainActivity.this.getResources().openRawResource(R.raw.spanish_words)
                                            ),
                                            new DictionaryRule(
                                                    MainActivity.this.getResources().openRawResource(R.raw.common_passwords)
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
        result.setText(codeToStringId.get(analysisResult.code()));

        colorizeResult(analysisResult.passwordIsStrong());
    }

    private void colorizeResult(boolean isStrong) {
        int color = R.color.result_weak;
        if (isStrong) {
            color = R.color.colorPrimaryDark;
        }
        result.setTextColor(ContextCompat.getColor(this, color));
    }
}

package es.jonatantierno.passwordcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import es.jonatantierno.passwordcoach.domain.model.Analysis;
import es.jonatantierno.passwordcoach.domain.model.rules.DictionaryRule;
import es.jonatantierno.passwordcoach.domain.model.rules.PasswordMeterRule;
import es.jonatantierno.passwordcoach.domain.model.rules.Result;
import es.jonatantierno.passwordcoach.domain.model.rules.ResultCode;
import es.jonatantierno.passwordcoach.domain.model.rules.SetOfRules;
import es.jonatantierno.passwordcoach.domain.model.rules.ShortPasswordRule;
import es.jonatantierno.passwordcoach.domain.model.tips.RandomTipSource;
import es.jonatantierno.passwordcoach.domain.model.tips.TipSource;
import es.jonatantierno.passwordcoach.domain.ports.Gui;
import es.jonatantierno.passwordcoach.domain.ports.TipDisplay;
import es.jonatantierno.passwordcoach.repositories.TipFrame;
import es.jonatantierno.passwordcoach.repositories.ZxcvbnPasswordMeter;

public class MainActivity extends AppCompatActivity implements Gui {

    public static final int MIN_LENGTH = 9;
    public static final int MIN_STRENGTH = 3;

    private EditText password;
    private TextView result;
    private Map<ResultCode, Integer> codeToStringId = buildCodeToStringId();

    private TipDisplay tipframe;
    private TipSource tipSource;

    private Map<ResultCode,Integer> buildCodeToStringId() {
        HashMap<ResultCode, Integer> map = new HashMap<>();
        map.put(ResultCode.TOO_SHORT, R.string.password_is_too_short);
        map.put(ResultCode.IN_DICTIONARY, R.string.password_is_in_dictionary);
        map.put(ResultCode.WEAK, R.string.password_is_weak);
        map.put(ResultCode.STRONG, R.string.password_is_strong);
        map.put(ResultCode.WEAK_ACCORDING_TO_METER, R.string.weak_according_to_meter);
        return map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tipframe = new TipFrame((ViewGroup) findViewById(R.id.tipLayout));
        tipSource = new RandomTipSource(
                getResources().getStringArray(R.array.advice_titles),
                getResources().getStringArray(R.array.advice_contents),
                getResources().getStringArray(R.array.technique_titles),
                getResources().getStringArray(R.array.technique_contents)
        );


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
                                            new DictionaryRule(
                                                    MainActivity.this.getResources().openRawResource(R.raw.spanish_words)
                                            ),
                                            new DictionaryRule(
                                                    MainActivity.this.getResources().openRawResource(R.raw.common_passwords)
                                            ),
                                            new PasswordMeterRule( new ZxcvbnPasswordMeter(), MIN_STRENGTH)
                                    )
                            )
                    ).start(password.getText().toString().trim());

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

        tipframe.show(tipSource.tip(analysisResult));
    }

    private void colorizeResult(boolean isStrong) {
        int color = R.color.result_weak;
        if (isStrong) {
            color = R.color.colorPrimaryDark;
        }
        result.setTextColor(ContextCompat.getColor(this, color));
    }
}

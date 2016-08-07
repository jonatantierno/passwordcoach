package es.jonatantierno.passwordcoach;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import es.jonatantierno.passwordcoach.domain.model.Analysis;
import es.jonatantierno.passwordcoach.domain.model.dictionary.RxDictionary;
import es.jonatantierno.passwordcoach.domain.model.rules.DictionaryRule;
import es.jonatantierno.passwordcoach.domain.model.rules.ObservableDictionaryRule;
import es.jonatantierno.passwordcoach.domain.model.rules.PasswordMeterRule;
import es.jonatantierno.passwordcoach.domain.model.rules.Result;
import es.jonatantierno.passwordcoach.domain.model.rules.ResultCode;
import es.jonatantierno.passwordcoach.domain.model.rules.SetOfRules;
import es.jonatantierno.passwordcoach.domain.model.rules.ShortPasswordRule;
import es.jonatantierno.passwordcoach.domain.model.tips.RandomTipSource;
import es.jonatantierno.passwordcoach.domain.model.tips.TipSource;
import es.jonatantierno.passwordcoach.domain.ports.Gui;
import es.jonatantierno.passwordcoach.repositories.TipFrame;
import es.jonatantierno.passwordcoach.repositories.ZxcvbnPasswordMeter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements Gui {

    public static final int MIN_LENGTH = 9;
    public static final int MIN_STRENGTH = 3;

    private EditText password;
    private TextView result;
    private Map<ResultCode, Integer> codeToStringId = buildCodeToStringId();

    private TipFrame tipframe;
    private TipSource tipSource;
    private boolean readyToLeave = true;
    CompositeSubscription compositeSubscription = new CompositeSubscription();

    private Map<ResultCode, Integer> buildCodeToStringId() {
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

        compositeSubscription.add(
                new RxDictionary(new TweetSource().asObservable(this)).asObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .reduce(new StringBuffer(), (buffer, word) -> buffer.append(" ").append(word))
                        .subscribe(
                                buffer -> writeString(buffer.toString()),
                                e -> writeString(e.toString())
                        )
        );

        setContentView(R.layout.activity_main);
        tipframe = new TipFrame((ViewGroup) findViewById(R.id.tipLayout));
        tipSource = new RandomTipSource(
                getResources().getStringArray(R.array.advice_titles),
                getResources().getStringArray(R.array.advice_contents),
                getResources().getStringArray(R.array.technique_titles),
                getResources().getStringArray(R.array.technique_contents)
        );


        password = (EditText) findViewById(R.id.passwordEditText);
        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                readyToLeave = false;
                hideKeyboard();

                new Analysis(
                        MainActivity.this,
                        new SetOfRules(
                                Arrays.asList(
                                        new ObservableDictionaryRule(
                                                new RxDictionary(new TweetSource().asObservable(this)).asObservable()
                                                        .subscribeOn(Schedulers.io())
                                        ),
                                        new ShortPasswordRule(MIN_LENGTH),
                                        new DictionaryRule(
                                                MainActivity.this.getResources().openRawResource(R.raw.spanish_words)
                                        ),
                                        new DictionaryRule(
                                                MainActivity.this.getResources().openRawResource(R.raw.common_passwords)
                                        ),
                                        new PasswordMeterRule(new ZxcvbnPasswordMeter(), MIN_STRENGTH)
                                )
                        )
                ).start(password.getText().toString().trim());

                return true;
            } else {
                return false;
            }
        });

        result = (TextView) findViewById(R.id.resultTextView);

    }

    @Override
    protected void onDestroy() {
        compositeSubscription.clear();
        super.onDestroy();
    }

    private void writeString(final String text) {
        Log.d("TAG", text);
        MainActivity.this.runOnUiThread(() -> result.setText(text));
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
    }

    private void showKeyboard() {
        password.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
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
        result.setTextColor(this.getResources().getColor(color));
    }

    @Override
    public void onBackPressed() {
        if (readyToLeave) {
            super.onBackPressed();
        } else {
            readyToLeave = true;
            tipframe.hide();
            password.setText("");
            showKeyboard();
        }
    }
}

package es.jonatantierno.passwordcoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import es.jonatantierno.passwordcoach.domain.model.dictionary.RxDictionary;
import es.jonatantierno.passwordcoach.infrastructure.ObservableTweets;
import es.jonatantierno.passwordcoach.infrastructure.PersistentBoolean;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DictionaryActivity extends AppCompatActivity {

    private Switch dictSwitch;
    private TextView dictionaryInfoTextView;
    private TextView dictionaryTextView;
    private View progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        dictionaryInfoTextView = (TextView) findViewById(R.id.dictionaryInfoTextView);
        dictionaryTextView = (TextView) findViewById(R.id.dictionaryTextView);
        dictSwitch = (Switch) findViewById(R.id.dictionarySwitch);
        progress = findViewById(R.id.dictProgress);

        dictSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchDictionary(isChecked);
            new PersistentBoolean(this).save(isChecked);
        });

        loadSwitch(new PersistentBoolean(this).load());
    }

    private void loadSwitch(boolean enabled) {
        dictSwitch.setChecked(enabled);
        switchDictionary(enabled);
    }

    private void switchDictionary(boolean isChecked) {
        if (isChecked) {

            progress.setVisibility(View.VISIBLE);

            new RxDictionary(new ObservableTweets(this).go()).asObservable()
                    .reduce(new StringBuffer(), (buffer, word) -> buffer.append(word).append(" \t \t \t "))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            buffer -> setDictionaryText(getString(R.string.dictionary_title), buffer.toString()),
                            e -> setDictionaryText(e.toString(), "")
                    );
        } else {
            setDictionaryText(getString(R.string.dictionary_info), "");
        }
    }

    private void setDictionaryText(String head, String body) {
        dictionaryInfoTextView.setText(head);
        dictionaryTextView.setText(body);
        progress.setVisibility(View.GONE);
    }
}

package es.jonatantierno.passwordcoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import es.jonatantierno.passwordcoach.domain.model.dictionary.RxDictionary;
import es.jonatantierno.passwordcoach.infrastructure.ObservableTweets;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DictionaryActivity extends AppCompatActivity {

    private Switch dictSwitch;
    private TextView dictionaryInfoTextView;
    private TextView dictionaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        dictionaryInfoTextView = (TextView) findViewById(R.id.dictionaryInfoTextView);
        dictionaryTextView = (TextView) findViewById(R.id.dictionaryTextView);
        dictSwitch = (Switch) findViewById(R.id.dictionarySwitch);
        dictSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchDictionary(isChecked);
        });
    }

    private void switchDictionary(boolean isChecked) {
        if (isChecked) {
            new RxDictionary(new ObservableTweets(this).go()).asObservable()
                    .reduce(new StringBuffer(), (buffer, word) -> buffer.append(" \t \t \t ").append(word))
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
    }
}

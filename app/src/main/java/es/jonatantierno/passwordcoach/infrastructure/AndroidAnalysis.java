package es.jonatantierno.passwordcoach.infrastructure;

import android.support.v4.app.FragmentActivity;

import java.util.Arrays;

import es.jonatantierno.passwordcoach.MainActivity;
import es.jonatantierno.passwordcoach.R;
import es.jonatantierno.passwordcoach.domain.model.Analysis;
import es.jonatantierno.passwordcoach.domain.model.dictionary.RxDictionary;
import es.jonatantierno.passwordcoach.domain.model.rules.DictionaryRule;
import es.jonatantierno.passwordcoach.domain.model.rules.ObservableDictionaryRule;
import es.jonatantierno.passwordcoach.domain.model.rules.PasswordMeterRule;
import es.jonatantierno.passwordcoach.domain.model.rules.Rule;
import es.jonatantierno.passwordcoach.domain.model.rules.SetOfRules;
import es.jonatantierno.passwordcoach.domain.model.rules.ShortPasswordRule;
import es.jonatantierno.passwordcoach.domain.model.rules.ToggableRule;
import es.jonatantierno.passwordcoach.domain.ports.Gui;
import es.jonatantierno.passwordcoach.infrastructure.repositories.ZxcvbnPasswordMeter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AndroidAnalysis extends Analysis {
    private static final int MIN_LENGTH = 9;
    private static final int MIN_STRENGTH = 3;

    public AndroidAnalysis(Gui gui, FragmentActivity activity) {
        super(
                gui,
                new SetOfRules(
                        Arrays.asList(
                                new ToggableRule(
                                        new PersistentBoolean(activity).load(),
                                        new ObservableDictionaryRule(
                                                new PersistentStringSetObservable(activity).load())
                                ),
                                new ShortPasswordRule(MIN_LENGTH),
                                new DictionaryRule(
                                        activity.getResources().openRawResource(R.raw.spanish_words)
                                ),
                                new DictionaryRule(
                                        activity.getResources().openRawResource(R.raw.common_passwords)
                                ),
                                new PasswordMeterRule(new ZxcvbnPasswordMeter(), MIN_STRENGTH)
                        )
                ),
                Schedulers.io(),
                AndroidSchedulers.mainThread());
    }
}

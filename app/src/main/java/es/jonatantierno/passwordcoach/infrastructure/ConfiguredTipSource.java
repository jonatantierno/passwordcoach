package es.jonatantierno.passwordcoach.infrastructure;

import android.content.Context;

import es.jonatantierno.passwordcoach.R;
import es.jonatantierno.passwordcoach.domain.model.tips.PersonalDictionaryTipSource;
import es.jonatantierno.passwordcoach.domain.model.tips.RandomTipSource;
import es.jonatantierno.passwordcoach.domain.model.tips.Tip;
import es.jonatantierno.passwordcoach.domain.model.tips.TipType;

public class ConfiguredTipSource extends PersonalDictionaryTipSource {
    public ConfiguredTipSource(Context context) {
        super(
                new PersistentBoolean(context).load(),
                new Tip(
                        TipType.ACTIVATE_PERSONAL_DICTIONARY,
                        context.getString(R.string.activateTipTitle),
                        context.getString(R.string.activateTipContent)),
                new RandomTipSource(
                        context.getResources().getStringArray(R.array.advice_titles),
                        context.getResources().getStringArray(R.array.advice_contents),
                        context.getResources().getStringArray(R.array.technique_titles),
                        context.getResources().getStringArray(R.array.technique_contents)
                )
        );
    }
}

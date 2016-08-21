package es.jonatantierno.passwordcoach.infrastructure;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import es.jonatantierno.passwordcoach.InfoActivity;
import es.jonatantierno.passwordcoach.R;
import es.jonatantierno.passwordcoach.domain.model.tips.TipType;
import rx.functions.Action1;

import static android.widget.ArrayAdapter.createFromResource;

public class TipSpinner {
    private final TextView title;
    private final Spinner spinner;
    private final Context context;
    private final Map<TipType, Action1<Spinner>> typeToSpinnerConf;
    private TipType tipType = TipType.TECHNIQUE;
    private boolean user= false;

    public TipSpinner(ViewGroup container, Context context) {
        this.context = context;
        typeToSpinnerConf = buildTipTypeToSpinnerConf(context);

        spinner = (Spinner) container.findViewById(R.id.more_info_spinner);
        title = (TextView) container.findViewById(R.id.spinner_title);
    }

    private int contents(TipType tipType) {
        if (tipType == TipType.ADVICE) return R.array.advice_contents;
        else return R.array.technique_contents;
    }

    private int titles(TipType tipType) {
        if (tipType == TipType.ADVICE) return R.array.advice_titles;
        else return R.array.technique_titles;
    }

    private int title(TipType tipType) {
        if (tipType == TipType.ADVICE) return R.string.advice_spinner_title;
        else return R.string.technique_spinner_title;
    }

    private Map<TipType, Action1<Spinner>> buildTipTypeToSpinnerConf(Context context) {
        HashMap<TipType, Action1<Spinner>> map = new HashMap<>();
        map.put(TipType.ADVICE, spinner -> conf(spinner, R.array.advice_titles, context));
        map.put(TipType.TECHNIQUE, spinner -> conf(spinner, R.array.technique_titles, context));
        map.put(TipType.EMPTY, spinner -> spinner.setVisibility(View.GONE));
        map.put(TipType.ACTIVATE_PERSONAL_DICTIONARY, spinner -> spinner.setVisibility(View.GONE));
        return map;
    }

    private void conf(Spinner spinner, int id, Context context) {
        spinner.setAdapter(createFromResource(context, id, android.R.layout.simple_spinner_item));
    }

    @NonNull
    private AdapterView.OnItemSelectedListener listener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (user) {
                    context.startActivity(new Intent(context, InfoActivity.class)
                            .putExtra(InfoActivity.TITLES, titles(tipType))
                            .putExtra(InfoActivity.CONTENT, contents(tipType))
                            .putExtra(InfoActivity.SELECTED, position)
                            .putExtra(InfoActivity.TYPE, R.string.password_advice));
                }
                user = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    public void setSpinner(TipType type) {
        user=false;

        spinner.setOnItemSelectedListener(listener());

        this.tipType = type;
        spinner.setVisibility(View.VISIBLE);
        title.setText(title(this.tipType));
        typeToSpinnerConf.get(this.tipType).call(spinner);

    }
}

package es.jonatantierno.passwordcoach.repositories;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import es.jonatantierno.passwordcoach.R;
import es.jonatantierno.passwordcoach.domain.model.tips.Tip;
import es.jonatantierno.passwordcoach.domain.model.tips.TipType;
import es.jonatantierno.passwordcoach.domain.ports.TipDisplay;

public class TipFrame implements TipDisplay {
    private final TextView message;
    private final TextView title;
    private final TextView content;
    private final ViewGroup container;
    private final Map<TipType, Integer> typeToStringId;

    private Map<TipType,Integer> buildTipTypeToStringId() {
        HashMap<TipType, Integer> map = new HashMap<>();
        map.put(TipType.ADVICE, R.string.advice);
        map.put(TipType.TECHNIQUE, R.string.advice_technique);
        map.put(TipType.EMPTY, R.string.empty);
        return map;
    }

    public TipFrame(ViewGroup container) {
        typeToStringId = buildTipTypeToStringId();
        this.container = container;
        message= (TextView) container.findViewById(R.id.advice_technique_textview);
        title = (TextView) container.findViewById(R.id.tip_title);
        content = (TextView) container.findViewById(R.id.tip_content);
    }

    @Override
    public void show(Tip tip) {
        message.setText(typeToStringId.get(tip.type));
        title.setText(tip.title);
        content.setText(tip.content);
        container.setVisibility(View.VISIBLE);
    }
}

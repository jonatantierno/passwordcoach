package es.jonatantierno.passwordcoach.infrastructure;

import es.jonatantierno.passwordcoach.domain.model.Analysis;
import es.jonatantierno.passwordcoach.domain.model.rules.Rule;
import es.jonatantierno.passwordcoach.domain.ports.Gui;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AndroidAnalysis extends Analysis {
    public AndroidAnalysis(Gui gui, Rule rule) {
        super(gui, rule, Schedulers.io(), AndroidSchedulers.mainThread());
    }
}

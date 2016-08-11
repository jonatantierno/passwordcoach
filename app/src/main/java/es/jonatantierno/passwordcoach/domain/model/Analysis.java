package es.jonatantierno.passwordcoach.domain.model;

import es.jonatantierno.passwordcoach.domain.model.rules.Result;
import es.jonatantierno.passwordcoach.domain.model.rules.Rule;
import es.jonatantierno.passwordcoach.domain.ports.Gui;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

public class Analysis {
    private final Gui gui;
    private final Rule rule;
    private Scheduler workScheduler;
    private Scheduler guiScheduler;

    public Analysis(Gui gui, Rule rule, Scheduler workScheduler, Scheduler guiScheduler) {
        this.gui = gui;
        this.rule = rule;
        this.workScheduler = workScheduler;
        this.guiScheduler = guiScheduler;
    }

    public void start(final String password) {
        Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                subscriber.onNext(
                        rule.analyze(password)
                );
                subscriber.onCompleted();
            }
        }).subscribeOn(workScheduler)
                .observeOn(guiScheduler)
                .subscribe(result -> gui.show(result));

    }
}

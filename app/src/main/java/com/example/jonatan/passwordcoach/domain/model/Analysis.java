package com.example.jonatan.passwordcoach.domain.model;

import com.example.jonatan.passwordcoach.domain.ports.Gui;
import com.example.jonatan.passwordcoach.domain.model.rules.Rule;
import com.example.jonatan.passwordcoach.domain.model.rules.Result;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class Analysis implements Observer<Result>{
    private final Gui gui;
    private final Rule rule;

    public Analysis(Gui gui, Rule rule) {
        this.gui = gui;
        this.rule = rule;
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
        }).subscribe(this);

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Result result) {
        gui.show(result);
    }
}

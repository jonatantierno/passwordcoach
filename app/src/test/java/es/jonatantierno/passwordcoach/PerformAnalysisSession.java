package es.jonatantierno.passwordcoach;

import es.jonatantierno.passwordcoach.domain.model.Analysis;
import es.jonatantierno.passwordcoach.domain.ports.Gui;
import es.jonatantierno.passwordcoach.domain.model.rules.Rule;
import es.jonatantierno.passwordcoach.domain.model.rules.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rx.schedulers.Schedulers.*;

@RunWith(MockitoJUnitRunner.class)
public class PerformAnalysisSession {
    @Mock
    Gui gui;
    @Mock
    Rule rule;
    @Mock
    Result result;

    String password = "Irrelevant value";

    @Test
    public void anyPassword(){
        when(rule.analyze(password)).thenReturn(result);

        new Analysis(gui, rule, immediate(), immediate()).start(password);

        verify(gui).show(result);
    }
}

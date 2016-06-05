package com.example.jonatan.passwordcoach;

import com.example.jonatan.passwordcoach.domain.model.Analysis;
import com.example.jonatan.passwordcoach.domain.ports.Gui;
import com.example.jonatan.passwordcoach.domain.model.rules.Rule;
import com.example.jonatan.passwordcoach.domain.model.rules.Result;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        new Analysis(gui, rule).start(password);

        verify(gui).show(result);
    }
}

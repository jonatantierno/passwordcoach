package com.example.jonatan.passwordcoach.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class checkSetOfRules {
    private String password = "Irrelevant value";
    @Mock
    private Rule subRule;
    @Mock
    private Rule subRule2;

    @Test
    public void emptySet(){
        Result result = new SetOfRules(EMPTY_LIST).analyze(password);

        assertThat(result.passwordIsStrong(), is(false));
    }

    @Test
    public void oneAnalysis() {
        when(subRule.analyze(password)).thenReturn(new StrongPasswordResult());

        Result result = new SetOfRules(singletonList(subRule)).analyze(password);

        assertThat(result, instanceOf(StrongPasswordResult.class));
    }

    @Test
    public void severalAnalysisOneIsWeak() {
        when(subRule.analyze(password)).thenReturn(new StrongPasswordResult());
        when(subRule2.analyze(password)).thenReturn(new WeakPasswordResult());


        Result result = new SetOfRules(asList(subRule,subRule2)).analyze(password);

        assertThat(result, instanceOf(WeakPasswordResult.class));
    }

    @Test
    public void severalAnalysisAllAreStrong() {
        when(subRule.analyze(password)).thenReturn(new StrongPasswordResult());
        when(subRule2.analyze(password)).thenReturn(new StrongPasswordResult());


        Result result = new SetOfRules(asList(subRule,subRule2)).analyze(password);

        assertThat(result, instanceOf(StrongPasswordResult.class));
    }
}

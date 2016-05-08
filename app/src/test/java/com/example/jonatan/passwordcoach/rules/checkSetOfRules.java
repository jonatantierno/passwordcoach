package com.example.jonatan.passwordcoach.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.singletonList;
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

    private Result subResultStrong = new StrongPasswordResult();
    private Result subResult2Weak = new WeakPasswordResult();

    @Test
    public void emptySet(){
        Result result = new SetOfRules(EMPTY_LIST).analyze(password);

        assertThat(result.passwordIsStrong(), is(false));
    }

    @Test
    public void oneAnalysis() {
        when(subRule.analyze(password)).thenReturn(subResultStrong);

        Result result = new SetOfRules(singletonList(subRule)).analyze(password);

        assertThat(result, is(subResultStrong));
    }

    @Test
    public void severalAnalysisOneIsWeak() {
        when(subRule.analyze(password)).thenReturn(subResultStrong);
        when(subRule2.analyze(password)).thenReturn(subResult2Weak);


        Result result = new SetOfRules(asList(subRule,subRule2)).analyze(password);

        assertThat(result, is(subResult2Weak));
    }
}

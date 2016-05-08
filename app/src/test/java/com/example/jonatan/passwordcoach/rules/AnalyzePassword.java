package com.example.jonatan.passwordcoach.rules;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AnalyzePassword {
    @Test
    public void emptyPassword() {
        Result result = new EmptyPasswordRule().analyze("");

        assertThat(result.passwordIsStrong(), is(false));
    }

    @Test
    public void strongPassword() {
        Result result = new EmptyPasswordRule().analyze("09q834jq40983()(");

        assertThat(result.passwordIsStrong(), is(true));
    }
}

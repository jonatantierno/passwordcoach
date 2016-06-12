package es.jonatantierno.passwordcoach.domain.model.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import es.jonatantierno.passwordcoach.domain.ports.PasswordMeter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordMeterRuleTest {
    @Mock
    PasswordMeter meter;

    @Test
    public void weakPassword(){
        String password = "password";
        when(meter.measure(password)).thenReturn(1);

        PasswordMeterRule rule = new PasswordMeterRule(meter, 2);

        assertThat(rule.analyze(password).passwordIsStrong(), is(false));
        assertThat(rule.analyze(password).code(), is(ResultCode.WEAK_ACCORDING_TO_METER));
    }

    @Test
    public void strongPassword(){
        String password = "strong%password";
        when(meter.measure(password)).thenReturn(4);

        PasswordMeterRule rule = new PasswordMeterRule(meter, 4);

        assertThat(rule.analyze(password).passwordIsStrong(), is(true));
    }
}

package es.jonatantierno.passwordcoach;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ZxcvbnTest {

    public static final int WEAK = 0;
    public static final int FAIR = 1;
    public static final int STRONG = 3;
    public static final int VERY_STRONG = 4;

    @Test
    public void smokeTest() {
        Zxcvbn meter = new Zxcvbn();

        Strength strength = meter.measure("password");
        assertThat(strength.getScore(), is(WEAK));
    }

    @Test
    public void leet() {
        Zxcvbn meter = new Zxcvbn();

        Strength strength = meter.measure("pa$sw0rd");
        assertThat(strength.getScore(), is(WEAK));
    }

    @Test
    public void movies() {
        Zxcvbn meter = new Zxcvbn();

        Strength strength = meter.measure("braveheart");
        assertThat(strength.getScore(), is(FAIR));
    }

    @Test
    public void moviesPlusUppercase() {
        Zxcvbn meter = new Zxcvbn();

        Strength strength = meter.measure("braVeHeart");
        assertThat(strength.getScore(), is(FAIR));
    }

    @Test
    public void moviesPlusSeparator() {
        Zxcvbn meter = new Zxcvbn();

        Strength strength = meter.measure("brave%heart");
        assertThat(strength.getScore(), is(STRONG));
    }

    @Test
    public void separator() {
        Zxcvbn meter = new Zxcvbn();

        Strength strength = meter.measure("..pass$werd..");
        assertThat(strength.getScore(), is(VERY_STRONG));
    }

    @Test
    public void random() {
        Zxcvbn meter = new Zxcvbn();

        Strength strength = meter.measure("f4k(76p*...");
        assertThat(strength.getScore(), is(VERY_STRONG));
    }
}

package es.jonatantierno.passwordcoach.repositories;

import com.nulabinc.zxcvbn.Zxcvbn;

import es.jonatantierno.passwordcoach.domain.ports.PasswordMeter;

public class ZxcvbnPasswordMeter implements PasswordMeter{
    Zxcvbn realMeter = new Zxcvbn();
    @Override
    public int measure(String password) {
        return realMeter.measure(password).getScore();
    }
}

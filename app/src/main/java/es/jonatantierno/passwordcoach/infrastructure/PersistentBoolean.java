package es.jonatantierno.passwordcoach.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.*;

public class PersistentBoolean {
    public static final String ENABLED = "ENABLED";
    private final Context context;

    public PersistentBoolean(Context context) {
        this.context = context;
    }

    public boolean load() {
        SharedPreferences storage = context.getSharedPreferences(ENABLED, MODE_PRIVATE);
        return storage.getBoolean(ENABLED, false);
    }

    public void save(boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ENABLED, MODE_PRIVATE).edit();
        editor.putBoolean(ENABLED, value).commit();
    }
}

package es.jonatantierno.passwordcoach.infrastructure;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import es.jonatantierno.passwordcoach.MainActivity;

public class KeyboardControl {
    private final EditText editText;
    private final Activity activity;

    public KeyboardControl(EditText editText, Activity activity) {

        this.editText = editText;
        this.activity = activity;
    }

    public void hide() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void show() {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}

package es.jonatantierno.passwordcoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import static android.widget.ArrayAdapter.createFromResource;

public class InfoActivity extends AppCompatActivity {

    public static final String TITLES = "TITLES";
    public static final String SELECTED = "SELECTED";
    public static final String TYPE = "TYPE";
    public static final String CONTENT = "CONTENT";

    private Spinner spinner;
    private TextView contentTextView;
    private int contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        spinner = (Spinner) findViewById(R.id.info_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                contentTextView.setText(getResources().getStringArray(contents)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do
            }
        });
        contentTextView = (TextView) findViewById(R.id.info_content);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(type());
        spinner.setAdapter(createFromResource(this, titles(), android.R.layout.simple_spinner_item));

        contents = getIntent().getIntExtra(CONTENT, R.array.technique_contents);

        spinner.setSelection(selected());
        contentTextView.setText(getResources().getStringArray(contents)[selected()]);
    }

    private int selected() {
        return getIntent().getIntExtra(SELECTED, 0);
    }

    private int titles() {
        return getIntent().getIntExtra(TITLES, R.array.technique_titles);
    }

    private int type() {
        return getIntent().getIntExtra(TYPE, R.string.password_creation);
    }
}

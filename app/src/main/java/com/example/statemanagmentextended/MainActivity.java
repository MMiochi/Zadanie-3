package com.example.statemanagmentextended;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_COUNT = "count";
    private TextView textViewCount;
    private EditText editTextInput;
    private CheckBox checkBoxOption;
    private Switch switchDarkMode;
    private TextView textViewAdditionalInfo;
    private CountViewModel countViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCount = findViewById(R.id.textViewCount);
        Button buttonIncrement = findViewById(R.id.buttonIncrement);
        editTextInput = findViewById(R.id.editTextInput);
        checkBoxOption = findViewById(R.id.checkBoxOption);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        textViewAdditionalInfo = findViewById(R.id.textViewAdditionalInfo);

        countViewModel = new ViewModelProvider(this).get(CountViewModel.class);

        if (savedInstanceState != null) {
            countViewModel.setCount(savedInstanceState.getInt(KEY_COUNT, 0));
            editTextInput.setText(savedInstanceState.getString("edit_text", ""));
            checkBoxOption.setChecked(savedInstanceState.getBoolean("checkbox", false));
            switchDarkMode.setChecked(savedInstanceState.getBoolean("switch", false));
        }

        updateCountText();
        updateAdditionalInfoVisibility(checkBoxOption.isChecked());

        buttonIncrement.setOnClickListener(v -> {
            countViewModel.incrementCount();
            updateCountText();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            findViewById(android.R.id.content).setBackgroundColor(isChecked ? Color.BLACK : Color.WHITE);
        });

        checkBoxOption.setOnCheckedChangeListener((buttonView, isChecked) -> updateAdditionalInfoVisibility(isChecked));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNT, countViewModel.getCount());
        outState.putString("edit_text", editTextInput.getText().toString());
        outState.putBoolean("checkbox", checkBoxOption.isChecked());
        outState.putBoolean("switch", switchDarkMode.isChecked());
    }

    private void updateCountText() {
        textViewCount.setText("Licznik: " + countViewModel.getCount());
    }

    private void updateAdditionalInfoVisibility(boolean isVisible) {
        textViewAdditionalInfo.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if (isVisible) {
            textViewAdditionalInfo.setText("Opcja zaznaczona");
        }
    }
}
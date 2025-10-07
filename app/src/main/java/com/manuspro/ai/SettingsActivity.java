package com.manuspro.ai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private EditText apiKeyInput;
    private MaterialButton saveButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        prefs = getSharedPreferences("ManusProAI", MODE_PRIVATE);

        initializeViews();
        loadSettings();
        setupListeners();
    }

    private void initializeViews() {
        apiKeyInput = findViewById(R.id.apiKeyInput);
        saveButton = findViewById(R.id.saveButton);
    }

    private void loadSettings() {
        String apiKey = prefs.getString("api_key", "");
        if (!apiKey.isEmpty()) {
            apiKeyInput.setText(apiKey);
        }
    }

    private void setupListeners() {
        saveButton.setOnClickListener(v -> saveSettings());
    }

    private void saveSettings() {
        String apiKey = apiKeyInput.getText().toString().trim();

        if (apiKey.isEmpty()) {
            Toast.makeText(this, "لطفاً کلید API را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        prefs.edit().putString("api_key", apiKey).apply();
        Toast.makeText(this, "تنظیمات با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

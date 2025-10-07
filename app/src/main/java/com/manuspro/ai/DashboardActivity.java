package com.manuspro.ai;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView totalMessages;
    private TextView imagesGenerated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        totalMessages = findViewById(R.id.totalMessages);
        imagesGenerated = findViewById(R.id.imagesGenerated);

        loadStatistics();
    }

    private void loadStatistics() {
        int messages = StatsManager.getTotalMessages(this);
        int images = StatsManager.getTotalImages(this);

        totalMessages.setText(String.valueOf(messages));
        imagesGenerated.setText(String.valueOf(images));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

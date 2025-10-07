package com.manuspro.ai;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;

public class ImageGeneratorActivity extends AppCompatActivity {

    private EditText imagePromptInput;
    private MaterialButton generateButton;
    private ProgressBar progressBar;
    private CardView imageCard;
    private ImageView generatedImage;
    private MaterialButton downloadButton;
    private MaterialButton shareButton;
    private AIService aiService;
    private String currentImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_generator);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupListeners();
        
        aiService = new AIService(this);
    }

    private void initializeViews() {
        imagePromptInput = findViewById(R.id.imagePromptInput);
        generateButton = findViewById(R.id.generateButton);
        progressBar = findViewById(R.id.progressBar);
        imageCard = findViewById(R.id.imageCard);
        generatedImage = findViewById(R.id.generatedImage);
        downloadButton = findViewById(R.id.downloadButton);
        shareButton = findViewById(R.id.shareButton);
    }

    private void setupListeners() {
        generateButton.setOnClickListener(v -> generateImage());
        downloadButton.setOnClickListener(v -> downloadImage());
        shareButton.setOnClickListener(v -> shareImage());
    }

    private void generateImage() {
        String prompt = imagePromptInput.getText().toString().trim();
        
        if (prompt.isEmpty()) {
            Toast.makeText(this, "لطفاً توضیحات تصویر را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        generateButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        imageCard.setVisibility(View.GONE);

        aiService.generateImage(prompt, new AIService.ImageCallback() {
            @Override
            public void onSuccess(String imageUrl) {
                runOnUiThread(() -> {
                    currentImageUrl = imageUrl;
                    loadImage(imageUrl);
                    progressBar.setVisibility(View.GONE);
                    generateButton.setEnabled(true);
                    imageCard.setVisibility(View.VISIBLE);
                    
                    StatsManager.incrementImages(ImageGeneratorActivity.this);
                    Toast.makeText(ImageGeneratorActivity.this, "تصویر با موفقیت تولید شد!", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    generateButton.setEnabled(true);
                    Toast.makeText(ImageGeneratorActivity.this, "خطا: " + error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .into(generatedImage);
    }

    private void downloadImage() {
        if (currentImageUrl != null) {
            Toast.makeText(this, "در حال دانلود تصویر...", Toast.LENGTH_SHORT).show();
            // Implement download functionality
        }
    }

    private void shareImage() {
        if (currentImageUrl != null) {
            Toast.makeText(this, "قابلیت اشتراک‌گذاری به زودی...", Toast.LENGTH_SHORT).show();
            // Implement share functionality
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

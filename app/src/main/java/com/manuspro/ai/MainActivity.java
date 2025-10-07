package com.manuspro.ai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messages;
    private EditText messageInput;
    private FloatingActionButton sendButton;
    private AIService aiService;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupListeners();
        
        aiService = new AIService(this);
        
        // Welcome message
        addMessage(new ChatMessage("Manus Pro AI", 
            "سلام! من دستیار هوشمند پیشرفته شما هستم. می‌توانم:\n\n" +
            "✓ پاسخ به سوالات پیچیده\n" +
            "✓ تولید تصویر از متن\n" +
            "✓ کدنویسی و debugging\n" +
            "✓ تحلیل داده\n" +
            "✓ جستجوی وب\n\n" +
            "چطور می‌تونم کمکتون کنم؟", 
            false));
    }

    private void initializeViews() {
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupRecyclerView() {
        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages);
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void setupListeners() {
        sendButton.setOnClickListener(v -> sendMessage());
        
        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_chat) {
                return true;
            } else if (itemId == R.id.nav_image) {
                startActivity(new Intent(MainActivity.this, ImageGeneratorActivity.class));
                return true;
            } else if (itemId == R.id.nav_dashboard) {
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    private void sendMessage() {
        String message = messageInput.getText().toString().trim();
        if (message.isEmpty()) {
            return;
        }

        // Add user message
        addMessage(new ChatMessage("شما", message, true));
        messageInput.setText("");

        // Show typing indicator
        addMessage(new ChatMessage("Manus Pro AI", "در حال تایپ...", false));

        // Get AI response
        aiService.getResponse(message, new AIService.ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    // Remove typing indicator
                    messages.remove(messages.size() - 1);
                    // Add AI response
                    addMessage(new ChatMessage("Manus Pro AI", response, false));
                    
                    // Update statistics
                    StatsManager.incrementMessages(MainActivity.this);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    messages.remove(messages.size() - 1);
                    Toast.makeText(MainActivity.this, "خطا: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void addMessage(ChatMessage message) {
        messages.add(message);
        chatAdapter.notifyItemInserted(messages.size() - 1);
        chatRecyclerView.smoothScrollToPosition(messages.size() - 1);
    }
}

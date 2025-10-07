package com.manuspro.ai;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIService {
    
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String IMAGE_API_URL = "https://api.openai.com/v1/images/generations";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    
    private OkHttpClient client;
    private Context context;
    private String apiKey;

    public AIService(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        
        SharedPreferences prefs = context.getSharedPreferences("ManusProAI", Context.MODE_PRIVATE);
        this.apiKey = prefs.getString("api_key", "");
    }

    public void getResponse(String message, ResponseCallback callback) {
        if (apiKey.isEmpty()) {
            callback.onError("لطفاً کلید API را در تنظیمات وارد کنید");
            return;
        }

        try {
            JSONObject json = new JSONObject();
            json.put("model", "gpt-4");
            json.put("messages", new JSONArray()
                    .put(new JSONObject()
                            .put("role", "system")
                            .put("content", "You are Manus Pro AI, an advanced AI assistant with unlimited capabilities. You can help with coding, data analysis, creative writing, and more. Always respond in Persian if the user writes in Persian."))
                    .put(new JSONObject()
                            .put("role", "user")
                            .put("content", message)));
            json.put("temperature", 0.7);
            json.put("max_tokens", 2000);

            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(OPENAI_API_URL)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError("خطا در ارتباط با سرور: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        callback.onError("خطای سرور: " + response.code());
                        return;
                    }

                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String content = jsonResponse
                                .getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        
                        callback.onSuccess(content);
                    } catch (Exception e) {
                        callback.onError("خطا در پردازش پاسخ: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("خطا: " + e.getMessage());
        }
    }

    public void generateImage(String prompt, ImageCallback callback) {
        if (apiKey.isEmpty()) {
            callback.onError("لطفاً کلید API را در تنظیمات وارد کنید");
            return;
        }

        try {
            JSONObject json = new JSONObject();
            json.put("model", "dall-e-3");
            json.put("prompt", prompt);
            json.put("n", 1);
            json.put("size", "1024x1024");
            json.put("quality", "hd");

            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(IMAGE_API_URL)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError("خطا در ارتباط با سرور: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        callback.onError("خطای سرور: " + response.code());
                        return;
                    }

                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String imageUrl = jsonResponse
                                .getJSONArray("data")
                                .getJSONObject(0)
                                .getString("url");
                        
                        callback.onSuccess(imageUrl);
                    } catch (Exception e) {
                        callback.onError("خطا در پردازش پاسخ: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callback.onError("خطا: " + e.getMessage());
        }
    }

    public interface ResponseCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public interface ImageCallback {
        void onSuccess(String imageUrl);
        void onError(String error);
    }
}

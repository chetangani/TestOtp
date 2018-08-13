package com.transvision.testotp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView otp_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        otp_msg = findViewById(R.id.otp_message_txt);
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //noinspection ConstantConditions
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String otp = intent.getStringExtra("message");
                otp_msg.setText(find_regex(otp, 8));
            }
        }
    };

    public String find_regex(String otp, int value) {
        Pattern pattern = Pattern.compile("(\\d{"+value+"})");
        //   \d is for a digit
        //   {} is the number of digits here 4.

        Matcher matcher = pattern.matcher(otp);
        String val = "";
        if (matcher.find()) {
            val = matcher.group(1);  // 4 digit number
        }
        return val;
    }
}

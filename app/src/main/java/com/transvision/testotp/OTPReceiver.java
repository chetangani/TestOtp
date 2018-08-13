package com.transvision.testotp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class OTPReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm;
//        String sms_str ="";

        if (bundle != null)
        {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            assert pdus != null;
            smsm = new SmsMessage[pdus.length];
            for (int i=0; i<smsm.length; i++){
                smsm[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                String senderNum = smsm[i].getDisplayOriginatingAddress();
                Log.d("debug", "senderNum: "+senderNum);
                String message = smsm[i].getDisplayMessageBody().split(":")[1];
                Log.d("debug", "message: "+message);
                message = message.substring(0, message.length()-1);
//                sms_str += "\r\nMessage: ";
//                sms_str += smsm[i].getMessageBody();
//                sms_str+= "\r\n";
//                Log.d("debug", "smsm: "+smsm[i]);
                String Sender = smsm[i].getOriginatingAddress();
                //Check here sender is yours
                Log.d("debug", "sender: "+Sender);
                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message",message);

                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);

            }
        }
    }
}

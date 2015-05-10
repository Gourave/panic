package com.wearhacks.panic.panic.smssending;

import android.telephony.SmsManager;

/**
 * Created by Gourave on 15-05-10.
 */
public class SmsServices {

    SmsManager smsManager;

    public SmsServices() {
        smsManager = SmsManager.getDefault();
    }

    public void sendTextMessage(String number, String message) {
        smsManager.sendTextMessage( number, null, message, null, null);
    }

}

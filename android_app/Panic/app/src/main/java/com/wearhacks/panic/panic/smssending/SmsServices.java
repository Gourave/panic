package com.wearhacks.panic.panic.smssending;

import android.telephony.SmsManager;

/*
To use this class:
    SmsServices message = new SmsServices();
    message.sendTextMessage("1234567890", "Hi.");

    Can replace "1234567890" and "Hi." with number to text and message to spend repsectively
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

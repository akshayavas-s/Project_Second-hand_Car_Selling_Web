package com.hms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class TwilioService {

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public String sendSms(String to, String messageBody) {

        Message message = Message.creator(
                new PhoneNumber(to),       // Receiver's phone number
                new PhoneNumber(twilioPhoneNumber),  // Twilio phone number
                messageBody                // Message body
        ).create();

        return "SMS sent successfully! Message SID: " + message.getSid();
    }
}

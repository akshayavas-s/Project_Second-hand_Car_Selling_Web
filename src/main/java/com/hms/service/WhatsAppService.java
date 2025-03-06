package com.hms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsAppNumber;

    public String sendWhatsAppMessage(String to, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(twilioWhatsAppNumber),
                messageBody
        ).create();
        return "WhatsApp message sent successfully! Message SID: " + message.getSid();
    }
}
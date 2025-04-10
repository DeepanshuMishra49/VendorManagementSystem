package com.vms.vendor_management.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSMS(String to, String message) {
        try {
            Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                message
            ).create();
        } catch (Exception e) {
            System.out.println("Error sending SMS: " + e.getMessage());
        }
    }
} 
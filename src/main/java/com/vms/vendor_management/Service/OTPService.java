package com.vms.vendor_management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private EmailService emailService;

    private final Map<String, String> otpMap = new HashMap<>();
    private final Map<String, Long> otpExpiryMap = new HashMap<>();
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes

    public String generateOTP(String email) {
        // Generate a 6-digit OTP
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000));
        
        // Store OTP and its expiry time
        otpMap.put(email, otp);
        otpExpiryMap.put(email, System.currentTimeMillis() + OTP_VALID_DURATION);
        
        return otp;
    }

    public boolean validateOTP(String email, String otp) {
        String storedOTP = otpMap.get(email);
        Long expiryTime = otpExpiryMap.get(email);
        
        if (storedOTP == null || expiryTime == null) {
            return false;
        }
        
        // Check if OTP is expired
        if (System.currentTimeMillis() > expiryTime) {
            // Clean up expired OTP
            otpMap.remove(email);
            otpExpiryMap.remove(email);
            return false;
        }
        
        // Validate OTP
        boolean isValid = storedOTP.equals(otp);
        
        // Clean up used OTP
        if (isValid) {
            otpMap.remove(email);
            otpExpiryMap.remove(email);
        }
        
        return isValid;
    }

    public void clearOTP(String email) {
        otpMap.remove(email);
        otpExpiryMap.remove(email);
    }
} 
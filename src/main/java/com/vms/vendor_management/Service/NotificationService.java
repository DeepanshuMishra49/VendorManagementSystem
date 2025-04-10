package com.vms.vendor_management.Service;

import com.vms.vendor_management.Entity.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SMSService smsService;

    // Send both email and SMS notifications
    public void sendVendorNotification(Vendor vendor, String subject, String message) {
        try {
            // Send email if email is available
            if (vendor.getEmail() != null && !vendor.getEmail().isEmpty()) {
                emailService.sendEmail(vendor.getEmail(), subject, message);
                logger.info("Email notification sent to vendor: {}", vendor.getName());
            } else {
                logger.warn("No email address found for vendor: {}", vendor.getName());
            }
            
            // Send SMS if phone number is available
            if (vendor.getPhoneNo() != null && !vendor.getPhoneNo().isEmpty()) {
                smsService.sendSMS(vendor.getPhoneNo(), message);
                logger.info("SMS notification sent to vendor: {}", vendor.getName());
            } else {
                logger.warn("No phone number found for vendor: {}", vendor.getName());
            }
        } catch (Exception e) {
            logger.error("Error sending notification to vendor {}: {}", vendor.getName(), e.getMessage());
            throw new RuntimeException("Failed to send notification: " + e.getMessage());
        }
    }

    // Send order confirmation
    public void sendOrderConfirmation(Vendor vendor, String orderId) {
        String subject = "New Purchase Order: " + orderId;
        String message = String.format(
            "Dear %s,\n\nA new purchase order %s has been created for you. " +
            "Please log in to your vendor portal to view the details.\n\n" +
            "Best regards,\nVendor Management System",
            vendor.getName(), orderId
        );
        sendVendorNotification(vendor, subject, message);
    }

    // Send order status update
    public void sendOrderStatusUpdate(Vendor vendor, String orderId, String status) {
        String subject = "Order Status Update: " + orderId;
        String message = String.format(
            "Dear %s,\n\nYour purchase order %s has been updated to status: %s\n\n" +
            "Best regards,\nVendor Management System",
            vendor.getName(), orderId, status
        );
        sendVendorNotification(vendor, subject, message);
    }

    // Send performance alert
    public void sendPerformanceAlert(Vendor vendor, String metric, double value) {
        String subject = "Performance Alert";
        String message = String.format(
            "Dear %s,\n\nThis is to inform you that your %s metric has fallen to %.2f. " +
            "Please take necessary actions to improve this metric.\n\n" +
            "Best regards,\nVendor Management System",
            vendor.getName(), metric, value
        );
        sendVendorNotification(vendor, subject, message);
    }

    // Send payment confirmation
    public void sendPaymentConfirmation(Vendor vendor, String orderId, double amount) {
        String subject = "Payment Processed: " + orderId;
        String message = String.format(
            "Dear %s,\n\nPayment of %.2f has been processed for order %s.\n\n" +
            "Best regards,\nVendor Management System",
            vendor.getName(), amount, orderId
        );
        sendVendorNotification(vendor, subject, message);
    }
}

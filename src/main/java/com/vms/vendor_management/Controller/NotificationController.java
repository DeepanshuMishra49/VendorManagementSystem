ot ting package com.vms.vendor_management.Controller;

import com.vms.vendor_management.Entity.Vendor;
import com.vms.vendor_management.Service.NotificationService;
import com.vms.vendor_management.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private VendorService vendorService;

    @PostMapping("/vendor/{vendorId}/order-confirmation")
    public ResponseEntity<?> sendOrderConfirmation(
            @PathVariable String vendorId,
            @RequestParam String orderId) {
        try {
            Vendor vendor = vendorService.getVendorById(vendorId);
            if (vendor == null) {
                return ResponseEntity.status(404).body("Vendor not found with ID: " + vendorId);
            }
            notificationService.sendOrderConfirmation(vendor, orderId);
            return ResponseEntity.ok("Order confirmation sent successfully to vendor: " + vendor.getName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending order confirmation: " + e.getMessage());
        }
    }

    @PostMapping("/vendor/{vendorId}/status-update")
    public ResponseEntity<?> sendStatusUpdate(
            @PathVariable String vendorId,
            @RequestParam String orderId,
            @RequestParam String status) {
        try {
            Vendor vendor = vendorService.getVendorById(vendorId);
            if (vendor == null) {
                return ResponseEntity.status(404).body("Vendor not found with ID: " + vendorId);
            }
            notificationService.sendOrderStatusUpdate(vendor, orderId, status);
            return ResponseEntity.ok("Status update sent successfully to vendor: " + vendor.getName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending status update: " + e.getMessage());
        }
    }

    @PostMapping("/vendor/{vendorId}/performance-alert")
    public ResponseEntity<?> sendPerformanceAlert(
            @PathVariable String vendorId,
            @RequestParam String metric,
            @RequestParam double value) {
        try {
            Vendor vendor = vendorService.getVendorById(vendorId);
            if (vendor == null) {
                return ResponseEntity.status(404).body("Vendor not found with ID: " + vendorId);
            }
            notificationService.sendPerformanceAlert(vendor, metric, value);
            return ResponseEntity.ok("Performance alert sent successfully to vendor: " + vendor.getName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending performance alert: " + e.getMessage());
        }
    }

    @PostMapping("/vendor/{vendorId}/payment-confirmation")
    public ResponseEntity<?> sendPaymentConfirmation(
            @PathVariable String vendorId,
            @RequestParam String orderId,
            @RequestParam double amount) {
        try {
            Vendor vendor = vendorService.getVendorById(vendorId);
            if (vendor == null) {
                return ResponseEntity.status(404).body("Vendor not found with ID: " + vendorId);
            }
            notificationService.sendPaymentConfirmation(vendor, orderId, amount);
            return ResponseEntity.ok("Payment confirmation sent successfully to vendor: " + vendor.getName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending payment confirmation: " + e.getMessage());
        }
    }
}

package com.vms.vendor_management.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "purchase_orders")
public class PurchaseOrder {
    @Id
    private String id;
    private String vendorId;      // Vendor ID
    private List<String> items;   // List of purchased items
    private Double totalAmount;
    private String status;        // PENDING, COMPLETED, CANCELED
    
    @CreatedDate
    private Date orderDate;       // Will be automatically set
}

@Component
class PurchaseOrderListener extends AbstractMongoEventListener<PurchaseOrder> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<PurchaseOrder> event) {
        PurchaseOrder purchaseOrder = event.getSource();
        if (purchaseOrder.getOrderDate() == null) {
            purchaseOrder.setOrderDate(new Date());
        }
        if (purchaseOrder.getStatus() == null) {
            purchaseOrder.setStatus("PENDING");
        }
    }
}


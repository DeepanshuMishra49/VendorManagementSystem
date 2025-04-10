package com.vms.vendor_management.Controller;

import com.vms.vendor_management.Entity.PurchaseOrder;
import com.vms.vendor_management.Entity.Vendor;
import com.vms.vendor_management.Service.PurchaseOrderService;
import com.vms.vendor_management.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping
    public ResponseEntity<?> getAllPurchaseOrder() {
        List<PurchaseOrder> all = purchaseOrderService.getAllPurchaseOrders();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder savedPurchaseOrder = purchaseOrderService.savePurchaseOrder(purchaseOrder);
        return new ResponseEntity<>(savedPurchaseOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseOrderById(@PathVariable String id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
        if(purchaseOrder != null) {
            return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePurchaseOrder(@PathVariable String id, @RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder updatedpurchaseOrder = purchaseOrderService.updatePurchaseOrder(id, purchaseOrder);
        if(updatedpurchaseOrder != null) {
            return new ResponseEntity<>(updatedpurchaseOrder, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable String id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

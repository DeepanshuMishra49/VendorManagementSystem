package com.vms.vendor_management.Service;

import com.vms.vendor_management.Entity.PurchaseOrder;
import com.vms.vendor_management.Repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
     private PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder savePurchaseOrder(PurchaseOrder PurchaseOrder) {
        return purchaseOrderRepository.save(PurchaseOrder);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(String id) {
        Optional<PurchaseOrder> PurchaseOrder = purchaseOrderRepository.findById(id);
        return PurchaseOrder.orElse(null);
    }

    public PurchaseOrder updatePurchaseOrder(String id, PurchaseOrder updatedPurchaseOrder) {
        if (purchaseOrderRepository.existsById(id)) {
            updatedPurchaseOrder.setId(id);
            return purchaseOrderRepository.save(updatedPurchaseOrder);
        }
        return null;
    }

    public void deletePurchaseOrder(String id) {
        purchaseOrderRepository.deleteById(id);
    }
}

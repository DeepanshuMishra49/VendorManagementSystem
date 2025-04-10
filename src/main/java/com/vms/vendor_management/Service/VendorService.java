package com.vms.vendor_management.Service;

import com.vms.vendor_management.Entity.PurchaseOrder;
import com.vms.vendor_management.Entity.Vendor;
import com.vms.vendor_management.Repository.PurchaseOrderRepository;
import com.vms.vendor_management.Repository.VendorRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        for (Vendor vendor : vendors) {
            List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByVendorId(vendor.getId());
            vendor.setPurchaseOrders(purchaseOrders.stream().map(PurchaseOrder::getId).toList());
        }
        return vendors;
    }

    public Vendor getVendorById(String id) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(id);
        if (vendorOpt.isPresent()) {
            Vendor vendor = vendorOpt.get();
            List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByVendorId(vendor.getId());
            vendor.setPurchaseOrders(purchaseOrders.stream().map(PurchaseOrder::getId).toList());
            return vendor;
        }
        return null;
    }

    public Vendor updateVendor(String id, Vendor updatedVendor) {
        if (vendorRepository.existsById(id)) {
            updatedVendor.setId(id);
            return vendorRepository.save(updatedVendor);
        }
        return null;
    }

    public void deleteVendor(String id) {
        vendorRepository.deleteById(id);
    }
}


package com.vms.vendor_management.Repository;

import com.vms.vendor_management.Entity.PurchaseOrder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder, String> {
    List<PurchaseOrder> findByVendorId(String vendorId);
}

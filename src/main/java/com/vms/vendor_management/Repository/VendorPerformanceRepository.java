package com.vms.vendor_management.Repository;


import com.vms.vendor_management.Entity.VendorPerformance;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorPerformanceRepository extends MongoRepository<VendorPerformance, String> {
    List<VendorPerformance> findByVendorId(String vendorId);
}

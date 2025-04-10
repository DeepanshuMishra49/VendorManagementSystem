package com.vms.vendor_management.Repository;

import com.vms.vendor_management.Entity.Vendor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendorRepository extends MongoRepository<Vendor, String> {
}

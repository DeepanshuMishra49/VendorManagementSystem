package com.vms.vendor_management.Controller;

import com.vms.vendor_management.Entity.Vendor;
import com.vms.vendor_management.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping
    public ResponseEntity<?> getAllVendors() {
        List<Vendor> all = vendorService.getAllVendors();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createVendor(@RequestBody Vendor vendor) {
        Vendor savedVendor = vendorService.saveVendor(vendor);
        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable String id) {
        Vendor vendor = vendorService.getVendorById(id);
        if(vendor != null) {
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(id, vendor);
        if(updatedVendor != null) {
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable String id) {
        vendorService.deleteVendor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

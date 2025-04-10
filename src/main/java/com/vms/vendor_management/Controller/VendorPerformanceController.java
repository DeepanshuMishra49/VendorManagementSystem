package com.vms.vendor_management.Controller;

import com.vms.vendor_management.Entity.VendorPerformance;
import com.vms.vendor_management.Service.VendorPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-performances")
public class VendorPerformanceController {

    @Autowired
    private VendorPerformanceService service;

    @PostMapping
    public VendorPerformance create(@RequestBody VendorPerformance performance) {
        return service.createPerformance(performance);
    }

    @GetMapping
    public List<VendorPerformance> getAll() {
        return service.getAllPerformances();
    }

    @GetMapping("/{id}")
    public VendorPerformance getById(@PathVariable String id) {
        return service.getPerformanceById(id).orElse(null);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<VendorPerformance> getByVendorId(@PathVariable String vendorId) {
        return service.getByVendorId(vendorId);
    }

    @PutMapping("/{id}")
    public VendorPerformance update(@PathVariable String id, @RequestBody VendorPerformance updated) {
        return service.updatePerformance(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deletePerformance(id);
    }
}

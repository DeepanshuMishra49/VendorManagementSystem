package com.vms.vendor_management.Service;

import com.vms.vendor_management.Entity.VendorPerformance;
import com.vms.vendor_management.Repository.VendorPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorPerformanceService {

    @Autowired
    private VendorPerformanceRepository repository;

    public VendorPerformance createPerformance(VendorPerformance performance) {
        return repository.save(performance);
    }

    public List<VendorPerformance> getAllPerformances() {
        return repository.findAll();
    }

    public Optional<VendorPerformance> getPerformanceById(String id) {
        return repository.findById(id);
    }

    public List<VendorPerformance> getByVendorId(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    public VendorPerformance updatePerformance(String id, VendorPerformance updated) {
        return repository.findById(id).map(existing -> {
            existing.setVendorId(updated.getVendorId());
            existing.setQualityScore(updated.getQualityScore());
            existing.setDeliveryScore(updated.getDeliveryScore());
            existing.setResponseTime(updated.getResponseTime());
            existing.setCostCompetitiveness(updated.getCostCompetitiveness());
            return repository.save(existing);
        }).orElse(null);
    }

    public void deletePerformance(String id) {
        repository.deleteById(id);
    }
}

package com.vms.vendor_management.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vendor_performances")
public class VendorPerformance {

    @Id
    private String id;
    private String vendorId;

    private int qualityScore;           // 0-100
    private int deliveryScore;          // 0-100
    private int responseTime;           // in minutes (lower is better)
    private int costCompetitiveness;    // 0-100

    @Transient
    private int overallScore;

    public void setQualityScore(int qualityScore) {
        validateScore(qualityScore, "Quality Score");
        this.qualityScore = qualityScore;
    }

    public void setDeliveryScore(int deliveryScore) {
        validateScore(deliveryScore, "Delivery Score");
        this.deliveryScore = deliveryScore;
    }

    public void setResponseTime(int responseTime) {
        if (responseTime < 0) {
            throw new IllegalArgumentException("Response time cannot be negative");
        }
        this.responseTime = responseTime;
    }

    public void setCostCompetitiveness(int costCompetitiveness) {
        validateScore(costCompetitiveness, "Cost Competitiveness");
        this.costCompetitiveness = costCompetitiveness;
    }

    private void validateScore(int score, String field) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(field + " must be between 0 and 100");
        }
    }

    private int getNormalizedResponseTimeScore() {
        int maxResponseTime = 1440;
        int score = (int) ((1.0 - Math.min(responseTime, maxResponseTime) / (double) maxResponseTime) * 100);
        return Math.max(score, 0);
    }

    public int getOverallScore() {
        int responseScore = getNormalizedResponseTimeScore();
        return (qualityScore + deliveryScore + costCompetitiveness + responseScore) / 4;
    }
}

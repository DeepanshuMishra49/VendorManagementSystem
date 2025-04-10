package com.vms.vendor_management.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vendors")
public class Vendor {

    @Id
    private String id;
    private String name;
    private String address;
    private String email;
    private String phoneNo;
    private List<String> purchaseOrders;
    private int VendorRating;

    public void setRatingReview(int VendorRating) {
        if (VendorRating > 10 && VendorRating < 0 ) {
            throw new IllegalArgumentException("VendorRating should be in between 0 to 10");
        }
        this.VendorRating = VendorRating;
    }
}

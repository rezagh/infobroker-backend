package au.nsw.revenue.infobroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsicBusinessNameDto {
    private String businessName;
    private String abn;
    private String businessNumber;
    private String status; // "Registered", "Cancelled"
    private LocalDate registrationDate;
    private LocalDate renewalDate;
    private LocalDate cancellationDate;
    private List<HolderDto> holders;
    private AddressDto principalPlaceOfBusiness;
    private String businessType;
    private String extractType; // "Current" or "Historical"

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HolderDto {
        private String name;
        private String acn;
        private String abn;
        private AddressDto address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDto {
        private String line1;
        private String line2;
        private String suburb;
        private String state;
        private String postcode;
    }
}

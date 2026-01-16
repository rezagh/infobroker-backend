package au.nsw.revenue.infobroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsicCompanyExtractDto {
    private String acn; // Australian Company Number
    private String abn; // Australian Business Number
    private String companyName;
    private String companyType; // e.g., "Proprietary Limited"
    private String status; // e.g., "Registered", "Deregistered"
    private LocalDate registrationDate;
    private LocalDate annualReviewDate;
    private AddressDto registeredOffice;
    private AddressDto principalPlaceOfBusiness;
    private List<OfficeholderDto> directors;
    private List<OfficeholderDto> secretaries;
    private ShareStructureDto shareStructure;
    private List<ChargeDto> charges;
    private String extractDate;
    private String extractType; // "Current" or "Historical"

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDto {
        private String line1;
        private String line2;
        private String suburb;
        private String state;
        private String postcode;
        private String country;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OfficeholderDto {
        private String fullName;
        private LocalDate dateOfBirth;
        private LocalDate appointmentDate;
        private LocalDate cessationDate;
        private AddressDto address;
        private String position; // "Director", "Secretary"
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShareStructureDto {
        private Long totalShares;
        private List<ShareClassDto> shareClasses;
        private List<ShareholderDto> shareholders;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShareClassDto {
        private String className;
        private Long numberOfShares;
        private String rights;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShareholderDto {
        private String name;
        private Long numberOfShares;
        private String shareClass;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChargeDto {
        private String chargeNumber;
        private String chargeType;
        private LocalDate lodgedDate;
        private String chargee;
        private String status;
    }
}

package au.nsw.revenue.infobroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NswLrsTitleSearchDto {
    private String folioIdentifier; // e.g., "1/12345"
    private Integer editionNumber;
    private LocalDate editionDate;
    private LandDescriptionDto landDescription;
    private String localGovernmentArea;
    private String parish;
    private String county;
    private OwnershipDto ownership;
    private List<EncumbranceDto> encumbrances;
    private List<String> notations;
    private String searchType; // "Current" or "Historical"
    private LocalDate searchDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandDescriptionDto {
        private String lotNumber;
        private String planNumber;
        private String planType; // "DP", "SP", etc.
        private String area;
        private String location;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnershipDto {
        private List<OwnerDto> owners;
        private String ownershipType; // "Sole", "Joint Tenants", "Tenants in Common"
        private String addressForService;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnerDto {
        private String fullName;
        private String share; // e.g., "1/2" for tenants in common
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EncumbranceDto {
        private String dealingNumber;
        private String encumbranceType; // "Mortgage", "Easement", "Covenant", "Caveat", "Lease"
        private LocalDate registeredDate;
        private String description;
        private List<String> parties; // Mortgagee, easement holder, etc.
    }
}

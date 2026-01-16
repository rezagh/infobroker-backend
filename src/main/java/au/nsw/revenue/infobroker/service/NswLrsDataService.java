package au.nsw.revenue.infobroker.service;

import au.nsw.revenue.infobroker.dto.NswLrsTitleSearchDto;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Arrays;

@Service
public class NswLrsDataService {

    /**
     * Stub method - In production, this would call ib.com.au API for NSW LRS data
     */
    public NswLrsTitleSearchDto getTitleSearch(String folioIdentifier, boolean includeHistorical) {
        NswLrsTitleSearchDto dto = new NswLrsTitleSearchDto();
        dto.setFolioIdentifier(folioIdentifier);
        dto.setEditionNumber(3);
        dto.setEditionDate(LocalDate.of(2024, 8, 15));
        dto.setLocalGovernmentArea("City of Sydney");
        dto.setParish("St James");
        dto.setCounty("Cumberland");
        dto.setSearchType(includeHistorical ? "Historical" : "Current");
        dto.setSearchDate(LocalDate.now());

        // Land Description
        NswLrsTitleSearchDto.LandDescriptionDto landDesc = new NswLrsTitleSearchDto.LandDescriptionDto();
        landDesc.setLotNumber("1");
        landDesc.setPlanNumber("12345");
        landDesc.setPlanType("DP");
        landDesc.setArea("500 square metres");
        landDesc.setLocation("123 Example Street, Sydney NSW 2000");
        dto.setLandDescription(landDesc);

        // Ownership
        NswLrsTitleSearchDto.OwnershipDto ownership = new NswLrsTitleSearchDto.OwnershipDto();
        ownership.setOwnershipType("Joint Tenants");
        ownership.setAddressForService("123 Example Street, Sydney NSW 2000");

        NswLrsTitleSearchDto.OwnerDto owner1 = new NswLrsTitleSearchDto.OwnerDto();
        owner1.setFullName("John Smith");
        owner1.setShare("1/2");

        NswLrsTitleSearchDto.OwnerDto owner2 = new NswLrsTitleSearchDto.OwnerDto();
        owner2.setFullName("Jane Smith");
        owner2.setShare("1/2");

        ownership.setOwners(Arrays.asList(owner1, owner2));
        dto.setOwnership(ownership);

        // Encumbrances
        NswLrsTitleSearchDto.EncumbranceDto mortgage = new NswLrsTitleSearchDto.EncumbranceDto();
        mortgage.setDealingNumber("AB123456");
        mortgage.setEncumbranceType("Mortgage");
        mortgage.setRegisteredDate(LocalDate.of(2020, 3, 15));
        mortgage.setDescription("Mortgage to secure all moneys");
        mortgage.setParties(Arrays.asList("Commonwealth Bank of Australia"));

        NswLrsTitleSearchDto.EncumbranceDto easement = new NswLrsTitleSearchDto.EncumbranceDto();
        easement.setDealingNumber("AB789012");
        easement.setEncumbranceType("Easement");
        easement.setRegisteredDate(LocalDate.of(2015, 6, 1));
        easement.setDescription("Right of carriageway 3 metres wide");
        easement.setParties(Arrays.asList("Sydney Water Corporation"));

        dto.setEncumbrances(Arrays.asList(mortgage, easement));

        // Notations
        dto.setNotations(Arrays.asList(
                "This title is subject to the provisions of the Conveyancing Act 1919",
                "Unregistered dealing: Plan lodged for registration"));

        return dto;
    }

    /**
     * Stub method for property sales history
     */
    public NswLrsTitleSearchDto getPropertySalesHistory(String folioIdentifier) {
        // For MVP, return same structure with additional historical data
        NswLrsTitleSearchDto dto = getTitleSearch(folioIdentifier, true);

        // Add historical transfer information in notations
        dto.getNotations().add("Transfer registered: 15/03/2020 - Consideration: $850,000");
        dto.getNotations().add("Previous transfer: 10/06/2015 - Consideration: $620,000");

        return dto;
    }
}

package au.nsw.revenue.infobroker.service;

import au.nsw.revenue.infobroker.dto.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class AsicDataService {

    /**
     * Stub method - In production, this would call ib.com.au API
     */
    public AsicCompanyExtractDto getCompanyExtract(String acn, boolean includeHistorical) {
        // Stubbed data based on real ASIC structure
        AsicCompanyExtractDto dto = new AsicCompanyExtractDto();
        dto.setAcn(acn);
        dto.setAbn("51" + acn); // ABN is typically derived from ACN
        dto.setCompanyName("Example Pty Ltd");
        dto.setCompanyType("Australian Proprietary Company, Limited By Shares");
        dto.setStatus("Registered");
        dto.setRegistrationDate(LocalDate.of(2015, 3, 15));
        dto.setAnnualReviewDate(LocalDate.of(2026, 3, 15));
        dto.setExtractDate(LocalDate.now().toString());
        dto.setExtractType(includeHistorical ? "Historical" : "Current");

        // Registered Office
        AsicCompanyExtractDto.AddressDto registeredOffice = new AsicCompanyExtractDto.AddressDto();
        registeredOffice.setLine1("Level 5, 100 George Street");
        registeredOffice.setSuburb("Sydney");
        registeredOffice.setState("NSW");
        registeredOffice.setPostcode("2000");
        registeredOffice.setCountry("Australia");
        dto.setRegisteredOffice(registeredOffice);

        // Principal Place of Business
        AsicCompanyExtractDto.AddressDto ppob = new AsicCompanyExtractDto.AddressDto();
        ppob.setLine1("Level 5, 100 George Street");
        ppob.setSuburb("Sydney");
        ppob.setState("NSW");
        ppob.setPostcode("2000");
        ppob.setCountry("Australia");
        dto.setPrincipalPlaceOfBusiness(ppob);

        // Directors
        AsicCompanyExtractDto.OfficeholderDto director1 = new AsicCompanyExtractDto.OfficeholderDto();
        director1.setFullName("John Smith");
        director1.setDateOfBirth(LocalDate.of(1975, 6, 20));
        director1.setAppointmentDate(LocalDate.of(2015, 3, 15));
        director1.setPosition("Director");

        AsicCompanyExtractDto.AddressDto directorAddress = new AsicCompanyExtractDto.AddressDto();
        directorAddress.setLine1("25 Park Avenue");
        directorAddress.setSuburb("Chatswood");
        directorAddress.setState("NSW");
        directorAddress.setPostcode("2067");
        directorAddress.setCountry("Australia");
        director1.setAddress(directorAddress);

        dto.setDirectors(Arrays.asList(director1));

        // Secretaries
        AsicCompanyExtractDto.OfficeholderDto secretary = new AsicCompanyExtractDto.OfficeholderDto();
        secretary.setFullName("Jane Doe");
        secretary.setDateOfBirth(LocalDate.of(1980, 9, 10));
        secretary.setAppointmentDate(LocalDate.of(2016, 7, 1));
        secretary.setPosition("Secretary");
        dto.setSecretaries(Arrays.asList(secretary));

        // Share Structure
        AsicCompanyExtractDto.ShareStructureDto shareStructure = new AsicCompanyExtractDto.ShareStructureDto();
        shareStructure.setTotalShares(1000L);

        AsicCompanyExtractDto.ShareClassDto ordinaryShares = new AsicCompanyExtractDto.ShareClassDto();
        ordinaryShares.setClassName("Ordinary");
        ordinaryShares.setNumberOfShares(1000L);
        ordinaryShares.setRights("Full voting rights, dividends");
        shareStructure.setShareClasses(Arrays.asList(ordinaryShares));

        AsicCompanyExtractDto.ShareholderDto shareholder = new AsicCompanyExtractDto.ShareholderDto();
        shareholder.setName("John Smith");
        shareholder.setNumberOfShares(1000L);
        shareholder.setShareClass("Ordinary");
        shareStructure.setShareholders(Arrays.asList(shareholder));

        dto.setShareStructure(shareStructure);

        // Charges
        AsicCompanyExtractDto.ChargeDto charge = new AsicCompanyExtractDto.ChargeDto();
        charge.setChargeNumber("123456789");
        charge.setChargeType("Fixed and Floating Charge");
        charge.setLodgedDate(LocalDate.of(2020, 5, 10));
        charge.setChargee("Commonwealth Bank of Australia");
        charge.setStatus("Current");
        dto.setCharges(Arrays.asList(charge));

        return dto;
    }

    /**
     * Stub method - In production, this would call ib.com.au API
     */
    public AsicBusinessNameDto getBusinessName(String query, boolean includeHistorical) {
        AsicBusinessNameDto dto = new AsicBusinessNameDto();
        dto.setBusinessName(query);
        dto.setAbn("51123456789");
        dto.setBusinessNumber("BN98765432");
        dto.setStatus("Registered");
        dto.setRegistrationDate(LocalDate.of(2018, 6, 1));
        dto.setRenewalDate(LocalDate.of(2027, 6, 1));
        dto.setBusinessType("Individual/Sole Trader");
        dto.setExtractType(includeHistorical ? "Historical" : "Current");

        // Holder
        AsicBusinessNameDto.HolderDto holder = new AsicBusinessNameDto.HolderDto();
        holder.setName("John Smith");
        holder.setAbn("51123456789");

        AsicBusinessNameDto.AddressDto holderAddress = new AsicBusinessNameDto.AddressDto();
        holderAddress.setLine1("123 Main Street");
        holderAddress.setSuburb("Parramatta");
        holderAddress.setState("NSW");
        holderAddress.setPostcode("2150");
        holder.setAddress(holderAddress);

        dto.setHolders(Arrays.asList(holder));

        // Principal Place of Business
        AsicBusinessNameDto.AddressDto ppob = new AsicBusinessNameDto.AddressDto();
        ppob.setLine1("123 Main Street");
        ppob.setSuburb("Parramatta");
        ppob.setState("NSW");
        ppob.setPostcode("2150");
        dto.setPrincipalPlaceOfBusiness(ppob);

        return dto;
    }

    /**
     * Stub method - In production, this would call ib.com.au API for ABN lookup
     */
    public AsicCompanyExtractDto getAbnLookup(String abn) {
        // For simplicity, reusing company extract structure
        return getCompanyExtract(abn.substring(2), false);
    }
}

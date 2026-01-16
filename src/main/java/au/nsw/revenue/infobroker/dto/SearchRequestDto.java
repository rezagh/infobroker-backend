package au.nsw.revenue.infobroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDto {
    private String searchType; // ASIC_COMPANY, ASIC_BUSINESS_NAME, NSW_LRS_TITLE
    private String query; // ACN, ABN, Business Name, Folio Identifier, etc.
    private Boolean includeHistorical = false;
}

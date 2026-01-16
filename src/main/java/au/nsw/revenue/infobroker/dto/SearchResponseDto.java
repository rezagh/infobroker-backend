package au.nsw.revenue.infobroker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDto {
    private String searchType;
    private String query;
    private Object result; // Can be AsicCompanyExtractDto, AsicBusinessNameDto, or NswLrsTitleSearchDto
    private Boolean fromCache;
    private Boolean isDuplicate;
    private String duplicateWarning;
    private String searchId;
}

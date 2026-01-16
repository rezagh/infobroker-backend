package au.nsw.revenue.infobroker.service;

import au.nsw.revenue.infobroker.dto.*;
import au.nsw.revenue.infobroker.model.SearchHistory;
import au.nsw.revenue.infobroker.model.User;
import au.nsw.revenue.infobroker.repository.SearchHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SearchService {

    @Autowired
    private AsicDataService asicDataService;

    @Autowired
    private NswLrsDataService nswLrsDataService;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private AuditService auditService;

    private final ObjectMapper objectMapper;

    public SearchService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public SearchResponseDto performSearch(SearchRequestDto request, User user, String ipAddress) {
        SearchResponseDto response = new SearchResponseDto();
        response.setSearchType(request.getSearchType());
        response.setQuery(request.getQuery());
        response.setSearchId(UUID.randomUUID().toString());

        // Check for cached result first
        Optional<SearchHistory> cachedSearch = searchHistoryRepository.findCachedResult(
                request.getSearchType(),
                request.getQuery(),
                LocalDateTime.now());

        if (cachedSearch.isPresent()) {
            response.setFromCache(true);
            response.setResult(deserializeResult(cachedSearch.get().getCachedResult(), request.getSearchType()));
        } else {
            response.setFromCache(false);

            // Perform actual search based on type
            Object result = executeSearch(request);
            response.setResult(result);

            // Save to cache
            saveSearchToHistory(user, request, result);
        }

        // Check for duplicates (searches in last 30 days)
        List<SearchHistory> duplicates = searchHistoryRepository.findRecentDuplicates(
                user,
                request.getSearchType(),
                request.getQuery(),
                LocalDateTime.now().minusDays(30));

        if (!duplicates.isEmpty()) {
            response.setIsDuplicate(true);
            response.setDuplicateWarning(
                    String.format("This search was previously performed %d time(s) in the last 30 days. " +
                            "Last search: %s",
                            duplicates.size(),
                            duplicates.get(0).getSearchedAt().toString()));
        } else {
            response.setIsDuplicate(false);
        }

        // Audit log
        auditService.logSearch(user, request.getSearchType(), request.getQuery(), ipAddress, true);

        return response;
    }

    private Object executeSearch(SearchRequestDto request) {
        return switch (request.getSearchType()) {
            case "ASIC_COMPANY" ->
                asicDataService.getCompanyExtract(request.getQuery(), request.getIncludeHistorical());
            case "ASIC_BUSINESS_NAME" ->
                asicDataService.getBusinessName(request.getQuery(), request.getIncludeHistorical());
            case "ASIC_ABN_LOOKUP" -> asicDataService.getAbnLookup(request.getQuery());
            case "NSW_LRS_TITLE" ->
                nswLrsDataService.getTitleSearch(request.getQuery(), request.getIncludeHistorical());
            case "NSW_LRS_SALES_HISTORY" -> nswLrsDataService.getPropertySalesHistory(request.getQuery());
            default -> throw new IllegalArgumentException("Unknown search type: " + request.getSearchType());
        };
    }

    private void saveSearchToHistory(User user, SearchRequestDto request, Object result) {
        SearchHistory history = new SearchHistory();
        history.setUser(user);
        history.setSearchType(request.getSearchType());
        history.setSearchQuery(request.getQuery());

        try {
            history.setSearchParameters(objectMapper.writeValueAsString(request));
            history.setCachedResult(objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            // Log error but don't fail the search
            history.setSearchParameters("{}");
            history.setCachedResult("{}");
        }

        history.setSearchedAt(LocalDateTime.now());
        history.setCacheExpiresAt(LocalDateTime.now().plusHours(24)); // Cache for 24 hours
        history.setResultsCount(1);

        searchHistoryRepository.save(history);
    }

    private Object deserializeResult(String json, String searchType) {
        try {
            return switch (searchType) {
                case "ASIC_COMPANY", "ASIC_ABN_LOOKUP" -> objectMapper.readValue(json, AsicCompanyExtractDto.class);
                case "ASIC_BUSINESS_NAME" -> objectMapper.readValue(json, AsicBusinessNameDto.class);
                case "NSW_LRS_TITLE", "NSW_LRS_SALES_HISTORY" ->
                    objectMapper.readValue(json, NswLrsTitleSearchDto.class);
                default -> json;
            };
        } catch (JsonProcessingException e) {
            return json;
        }
    }

    public List<SearchHistory> getUserSearchHistory(User user) {
        return searchHistoryRepository.findByUserOrderBySearchedAtDesc(user);
    }
}

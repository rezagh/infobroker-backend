package au.nsw.revenue.infobroker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String searchType; // ASIC_COMPANY, ASIC_BUSINESS_NAME, NSW_LRS_TITLE, etc.

    @Column(nullable = false)
    private String searchQuery;

    @Column(length = 5000)
    private String searchParameters; // JSON string

    @Column(nullable = false)
    private LocalDateTime searchedAt = LocalDateTime.now();

    private Integer resultsCount = 0;

    private Boolean isDuplicate = false;

    @Column(length = 10000)
    private String cachedResult; // JSON string of the result

    private LocalDateTime cacheExpiresAt;
}

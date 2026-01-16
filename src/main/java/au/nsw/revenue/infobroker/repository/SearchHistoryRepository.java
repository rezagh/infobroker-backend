package au.nsw.revenue.infobroker.repository;

import au.nsw.revenue.infobroker.model.SearchHistory;
import au.nsw.revenue.infobroker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserOrderBySearchedAtDesc(User user);

    @Query("SELECT s FROM SearchHistory s WHERE s.user = :user AND s.searchType = :searchType AND s.searchQuery = :query AND s.searchedAt > :since")
    List<SearchHistory> findRecentDuplicates(
            @Param("user") User user,
            @Param("searchType") String searchType,
            @Param("query") String query,
            @Param("since") LocalDateTime since);

    @Query("SELECT s FROM SearchHistory s WHERE s.searchType = :searchType AND s.searchQuery = :query AND s.cacheExpiresAt > :now ORDER BY s.searchedAt DESC")
    Optional<SearchHistory> findCachedResult(
            @Param("searchType") String searchType,
            @Param("query") String query,
            @Param("now") LocalDateTime now);
}

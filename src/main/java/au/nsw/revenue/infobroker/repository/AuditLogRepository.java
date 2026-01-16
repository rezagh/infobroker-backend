package au.nsw.revenue.infobroker.repository;

import au.nsw.revenue.infobroker.model.AuditLog;
import au.nsw.revenue.infobroker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserOrderByTimestampDesc(User user);

    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    List<AuditLog> findByActionAndTimestampAfter(String action, LocalDateTime timestamp);
}

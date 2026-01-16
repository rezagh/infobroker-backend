package au.nsw.revenue.infobroker.service;

import au.nsw.revenue.infobroker.model.AuditLog;
import au.nsw.revenue.infobroker.model.User;
import au.nsw.revenue.infobroker.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logAction(User user, String action, String resource, String details,
            String ipAddress, String userAgent, boolean success, String errorMessage) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResource(resource);
        log.setDetails(details);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setTimestamp(LocalDateTime.now());
        log.setSuccess(success);
        log.setErrorMessage(errorMessage);

        auditLogRepository.save(log);
    }

    public void logLogin(User user, String ipAddress, String userAgent, boolean success) {
        logAction(user, "LOGIN", "AUTH", null, ipAddress, userAgent, success, null);
    }

    public void logLogout(User user, String ipAddress, String userAgent) {
        logAction(user, "LOGOUT", "AUTH", null, ipAddress, userAgent, true, null);
    }

    public void logSearch(User user, String searchType, String query, String ipAddress, boolean success) {
        String details = String.format("{\"searchType\":\"%s\",\"query\":\"%s\"}", searchType, query);
        logAction(user, "SEARCH", searchType, details, ipAddress, null, success, null);
    }

    public void logDownload(User user, String resourceType, String resourceId, String ipAddress) {
        String details = String.format("{\"resourceType\":\"%s\",\"resourceId\":\"%s\"}", resourceType, resourceId);
        logAction(user, "DOWNLOAD", resourceType, details, ipAddress, null, true, null);
    }

    public List<AuditLog> getUserAuditLogs(User user) {
        return auditLogRepository.findByUserOrderByTimestampDesc(user);
    }

    public List<AuditLog> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByTimestampBetween(start, end);
    }
}

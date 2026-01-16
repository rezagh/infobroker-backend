package au.nsw.revenue.infobroker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String action; // SEARCH, LOGIN, LOGOUT, DOWNLOAD, etc.

    @Column(nullable = false)
    private String resource; // ASIC_COMPANY, NSW_LRS_TITLE, etc.

    @Column(length = 2000)
    private String details; // JSON string with search parameters

    @Column(nullable = false)
    private String ipAddress;

    private String userAgent;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    private Boolean success = true;

    private String errorMessage;
}

package vn.edu.ntu.csms.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift_swap_requests")
@Data
public class ShiftSwapRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_shift_id", nullable = false)
    private Shift currentShift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_shift_id", nullable = false)
    private Shift targetShift;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SwapStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum SwapStatus { PENDING, APPROVED, REJECTED }
}
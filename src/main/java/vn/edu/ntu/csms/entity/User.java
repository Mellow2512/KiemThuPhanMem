package vn.edu.ntu.csms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id", length = 10)
    private String id; // VD: NV001

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_id")
    private Role role; // Sử dụng Enum: ROLE_STAFF, ROLE_MANAGER

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status; // Enum: PENDING, ACTIVE, INACTIVE

    public enum Role { ROLE_STAFF, ROLE_MANAGER }
    public enum AccountStatus { PENDING, ACTIVE, INACTIVE }
}
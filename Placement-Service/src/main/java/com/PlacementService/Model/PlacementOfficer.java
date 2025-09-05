package com.PlacementService.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "placement_officers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacementOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Officer details
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;  // store hashed (BCrypt)

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String collegeName;

    @Column(nullable = false)
    private String designation;  // Example: "Training & Placement Officer"
    
    private String idproof;
    // Status & role
    @Column(nullable = false)
    private String status = "PENDING";  // PENDING, APPROVED, REJECTED

    @Column(nullable = false)
    private String role = "ROLE_TPO";   // fixed role
}

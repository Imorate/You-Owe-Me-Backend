package ir.imorate.yom.security.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "User")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 32, unique = true)
    private String username;

    @Column(nullable = false, length = 128)
    @ToString.Exclude
    private String password;

    @Column(nullable = false, length = 32, unique = true)
    private String email;

    @Column(nullable = false)
    @Builder.Default
    private Boolean accountNonExpired = Boolean.TRUE;

    @Column(nullable = false)
    @Builder.Default
    private Boolean accountNonLocked = Boolean.TRUE;

    @Column(nullable = false)
    @Builder.Default
    private Boolean credentialsNonExpired = Boolean.TRUE;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = Boolean.FALSE;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles;

}
package ir.imorate.yom.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Table
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false, unique = true, length = 16)
    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Column(nullable = false)
    private String description;

}
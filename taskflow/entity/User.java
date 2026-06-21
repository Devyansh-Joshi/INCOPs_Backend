package com.devyansh.taskflow.entity;

//import com.devyansh.taskflow.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<ProjectMember> projects =
            new HashSet<>();
}

package com.devyansh.taskflow.entity;

import com.devyansh.taskflow.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        MEMBER USER
    */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
        PROJECT
    */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /*
        ROLE INSIDE PROJECT
    */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectRole role;
}
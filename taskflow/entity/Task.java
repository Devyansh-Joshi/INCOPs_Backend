package com.devyansh.taskflow.entity;

import com.devyansh.taskflow.enums.Priority;
import com.devyansh.taskflow.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate dueDate;

    /*
        TASK BELONGS TO PROJECT
    */
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    /*
        ASSIGNED USER
    */
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    /*
        LABELS
    */
    @ManyToMany
    @JoinTable(
            name = "task_labels",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();

    @OneToMany(mappedBy = "task")
    private Set<TaskActivity> activities =
            new HashSet<>();
}
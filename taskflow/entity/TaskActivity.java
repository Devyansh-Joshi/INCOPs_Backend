package com.devyansh.taskflow.entity;

import com.devyansh.taskflow.enums.ActivityAction;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        TASK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    /*
        USER WHO PERFORMED ACTION
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
        ACTION TYPE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityAction action;

    /*
        HUMAN READABLE DETAILS
     */
    @Column(nullable = false, length = 1000)
    private String details;
}
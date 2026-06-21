package com.devyansh.taskflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /*
        COMMENT BELONGS TO TASK
    */
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    /*
        COMMENT AUTHOR
    */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
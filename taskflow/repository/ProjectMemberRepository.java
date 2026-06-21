package com.devyansh.taskflow.repository;

import com.devyansh.taskflow.entity.Project;
import com.devyansh.taskflow.entity.ProjectMember;
import com.devyansh.taskflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository
        extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByUser(User user);

    List<ProjectMember> findByProject(Project project);

    Boolean existsByUserAndProject(
            User user,
            Project project
    );

    void deleteByProject(Project project);

    Optional<ProjectMember> findByUserAndProject(
            User user,
            Project project
    );
}
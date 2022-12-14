package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM project WHERE id IN :listId", nativeQuery = true)
    int deleteByListId(@Param("listId") List<Long> listId);

}

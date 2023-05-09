package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Activity;
import com.ansv.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task>, TaskRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM task WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);

    @Transactional
    @Modifying
    List<Task> findByParentId(Long id);

    @Query(value = "SELECT * FROM task WHERE section_id = :sectionId AND project_id = :projectId", nativeQuery = true)
    List<Task> findBySectionIdAndProjectId(@Param("sectionId") Long sectionId, @Param("projectId") Long projectId);

//    @Transactional
//    @Modifying
////    @Query(value = "SELECT COUNT(*) FROM task WHERE parent_id = :id", nativeQuery = true)
////    Long countByParentId(@Param("id") Long id);
//    Long countByParentId(Long id);

}

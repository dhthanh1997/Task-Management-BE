package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>, JpaSpecificationExecutor<Assignment>, AssignmentRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM assigment WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);

    List<Assignment> findByTaskId(Long taskId);

    @Transactional
    @Modifying
    void deleteByTaskId(Long taskId);

    @Query(value = "SELECT member_id FROM assignment WHERE task_id = :taskId", nativeQuery = true)
    List<Long> getMemberIdByTaskId(Long taskId);

}

package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Activity;
import com.ansv.taskmanagement.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, PermissionRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM permission WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);

}

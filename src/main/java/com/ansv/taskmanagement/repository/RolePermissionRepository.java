package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Activity;
import com.ansv.taskmanagement.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>, RolePermissionRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM role_permission WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);

    @Query(value = "SELECT permission_id FROM role_permission WHERE role_id = :roleId", nativeQuery = true)
    List<Long> getPermissionIdByRoleId(@Param("roleId") Long  roleId);

    @Transactional
    @Modifying
    Integer deleteByRoleId(Long roleId);

}

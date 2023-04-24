package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission>,PermissionRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM permission WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);


    @Query(value = "SELECT p.* FROM permission as p LEFT JOIN role_permission as rp ON p.id = rp.permission_id \n" +
            "WHERE 1=1 AND rp.role_id = :roleId", nativeQuery = true)
    List<Permission> getAllByRoleId(@Param("roleId") Long roleId);

    Optional<Permission> findByCode(String code);


}

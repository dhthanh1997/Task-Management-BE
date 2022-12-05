package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.RoleOfApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleOfApplicationRepository extends JpaRepository<RoleOfApplication, Long>, RoleOfApplicationRepositoryCustom {

    @Query(value = "DELETE FROM role_of_application WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);

}

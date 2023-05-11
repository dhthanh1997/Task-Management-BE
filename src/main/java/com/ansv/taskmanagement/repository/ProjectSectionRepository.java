package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.ProjectSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProjectSectionRepository extends JpaRepository<ProjectSection, Long>, JpaSpecificationExecutor<ProjectSection>, ProjectSectionRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM project_section WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);


    @Transactional
    @Modifying
    Integer deleteBySectionId(Long roleId);

}

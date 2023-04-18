package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Activity;
import com.ansv.taskmanagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM member WHERE id IN :listId", nativeQuery = true)
    Integer deleteByListId(@Param("listId") List<Long> listId);

    @Transactional
    @Modifying
    void deleteById(Long id);

    Optional<Member> findByUsername(String username);


}

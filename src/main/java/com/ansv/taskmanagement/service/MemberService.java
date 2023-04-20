package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberService {

    MemberDTO findById(Long id);

    MemberDTO findByUsername(String username);

    MemberDTO save(MemberDTO item);

    List<MemberDTO> findAll();

    List<MemberDTO> search(Map<String, Object> mapParam);

    Page<MemberDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);


}
package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.TeamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TeamService {

    TeamDTO findById(Long id);

    TeamDTO save(TeamDTO item);

    List<TeamDTO> findAll();

    List<TeamDTO> search(Map<String, Object> mapParam);

    Page<TeamDTO> findBySearchCriteria(Optional<String> search, Pageable page);

    void deleteById(Long id);

    Integer deleteByListId(List<Long> id);

}
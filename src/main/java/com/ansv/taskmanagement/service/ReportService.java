package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.request.TaskImportDTO;
import com.ansv.taskmanagement.dto.response.ReportDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReportService {


    List<ReportDTO> findAll();

    List<ReportDTO> search(Map<String, Object> mapParam);

    Page<ReportDTO> findBySearchCriteria(Optional<String> search, Pageable page);




}
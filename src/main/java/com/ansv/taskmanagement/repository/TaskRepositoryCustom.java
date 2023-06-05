package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.dto.response.report.ProjectAndTaskReportDTO;
import com.ansv.taskmanagement.model.Activity;
import com.ansv.taskmanagement.model.ProjectAndTaskReportView;
import com.ansv.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;

public interface TaskRepositoryCustom {

    List<Task> previewReport(Map<String, Object> parameters);


}

package com.ansv.taskmanagement.repository.view;

import com.ansv.taskmanagement.model.ReportView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//@Component("ReportViewRepository")
//@Repository
public interface ReportViewRepository extends ReadOnlyRepository<ReportView, Long> {

//    List<ReportView> findDataWithParams(Map<String, Object> parameters);

}

package com.ansv.taskmanagement.repository.impl;

import com.ansv.taskmanagement.dto.response.report.ProjectAndTaskReportDTO;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.TaskRepositoryCustom;
import com.ansv.taskmanagement.util.DataUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepositoryCustomImpl extends BaseCustomRepository<Task> implements TaskRepositoryCustom {

    @Override
    public List<Task> previewReport(Map<String, Object> parameters) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder result = new StringBuilder();
        String query = "SELECT row_number() OVER () AS id, t.project_id AS project_id, t.id AS task_id, mem.username AS username, t.state AS state, team_mem.team_id AS team_id \n";
        String from = "FROM task AS t \n";
//        String join_project = "LEFT JOIN project AS p ON p.id = t.project_id \n";
        String join_task_member = "LEFT JOIN assignment AS ass ON ass.task_id = t.id \n";
        String join_member = "LEFT JOIN member AS mem ON mem.id = ass.member_id \n";
//        String join_team = "LEFT JOIN team AS te ON te.id = mem.team_id \n";
        String join_team_member = "LEFT JOIN team_member AS team_mem ON team_mem.member_id = mem.id \n";

        String where = "WHERE 1=1 \n";

        result.append(query + from + join_task_member + join_member + join_team_member + where);

        if(!DataUtils.isNullOrEmpty(parameters.get("username"))) {
            String where_username = "AND mem.username = :username \n";
            result.append(where_username);
            params.put("username", parameters.get("username").toString());
        }

        if(!DataUtils.isNullOrEmpty(parameters.get("teamId"))) {
            String where_team = "AND team_member.team_id = :teamId \n";
            result.append(where_team);
            params.put("teamId", DataUtils.parseToLong(parameters.get("teamId").toString()));
        }

        if(!DataUtils.isNullOrEmpty(parameters.get("projectId"))) {
            String where_project = "AND t.project_id = :projectId \n";
            result.append(where_project);
            params.put("projectId", DataUtils.parseToLong(parameters.get("projectId").toString()));
        }

        if(!DataUtils.isNullOrEmpty(parameters.get("fromDate"))) {
            String where_date = "AND CONVERT(DATE, p.created_date) >= CONVERT(DATE, :fromDate) \n";
            result.append(where_date);
            params.put("fromDate", parameters.get("fromDate").toString());
        }

        if(!DataUtils.isNullOrEmpty(parameters.get("toDate"))) {
            String where_date = "AND CONVERT(DATE, p.created_date) <= CONVERT(DATE, :toDate) \n";
            result.append(where_date);
            params.put("toDate", parameters.get("toDate").toString());
        }


        return getResultList(result.toString(), Task.class, params);
    }

    @Override
    public String buildQuery(Map<String, Object> paramSearch, Map<String, Object> parameters, boolean count) {
        return null;
    }
}

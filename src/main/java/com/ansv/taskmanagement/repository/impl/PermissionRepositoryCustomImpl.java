package com.ansv.taskmanagement.repository.impl;

import com.ansv.taskmanagement.model.Permission;
import com.ansv.taskmanagement.model.Tree;
import com.ansv.taskmanagement.repository.PermissionRepositoryCustom;
import com.ansv.taskmanagement.util.TreeComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PermissionRepositoryCustomImpl extends BaseCustomRepository<Permission> implements PermissionRepositoryCustom {

    @Override
    public List<Tree> getPermissionRecursive() {
        String sql = "WITH RECURSIVE menu_recursive\n" +
                "AS\n" +
                "(\n" +
                "SELECT rp.id, rp.name, rp.code, rp.parent_code, rp.description, rp.type,  1 AS depth\n" +
                "FROM permission AS rp\n" +
                "WHERE rp.parent_code IS NULL OR rp.parent_code = ''\n" +
                "UNION ALL\n" +
                "SELECT rp2.id, rp2.name, rp2.code, rp2.parent_code, rp2.description, rp2.type, mr.depth + 1\n" +
                "FROM permission AS rp2\n" +
                "INNER JOIN menu_recursive AS mr ON mr.code = rp2.parent_code\n" +
                ")\n" +
                "SELECT * FROM menu_recursive \n";

        return getResultList(sql, Tree.class, null);
    }

    @Override
    public String buildQuery(Map<String, Object> paramSearch, Map<String, Object> parameters, boolean count) {
        return null;
    }


}

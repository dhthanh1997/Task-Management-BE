package com.ansv.taskmanagement.repository;

import com.ansv.taskmanagement.model.Activity;
import com.ansv.taskmanagement.model.Permission;
import com.ansv.taskmanagement.model.Tree;
import com.ansv.taskmanagement.util.TreeComponent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PermissionRepositoryCustom {

    List<Tree> getPermissionRecursive();

}

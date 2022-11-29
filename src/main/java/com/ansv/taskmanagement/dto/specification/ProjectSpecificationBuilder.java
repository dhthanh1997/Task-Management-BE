package com.ansv.taskmanagement.dto.specification;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.criteria.SearchOperation;
import com.ansv.taskmanagement.model.Project;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecificationBuilder {

    private final List<SearchCriteria> params;

    public ProjectSpecificationBuilder(List<SearchCriteria> searchCriteria) {
        this.params = searchCriteria;
    }

    public ProjectSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final ProjectSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final ProjectSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Project> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<Project> result = new ProjectSpecification(params.get(0));

        for (int id = 1; id < params.size(); id++) {
            SearchCriteria searchCriteria = params.get(id);

            result = SearchOperation.getDataOption(searchCriteria.getOperation()) == SearchOperation.ALL
                    ? Specification.where(result).and(new ProjectSpecification(searchCriteria))
                    : Specification.where(result).or(new ProjectSpecification(searchCriteria));
        }

        return result;
    }


}

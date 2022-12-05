package com.ansv.taskmanagement.dto.specification;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.criteria.SearchOperation;
import com.ansv.taskmanagement.model.Activity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ActivitySpecificationBuilder {
    private final List<SearchCriteria> params;


    public ActivitySpecificationBuilder(List<SearchCriteria> params) {
        this.params = params;
    }

    public ActivitySpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final ActivitySpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final ActivitySpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Activity> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<Activity> result = new ActivitySpecification(params.get(0));

        for (int id = 1; id < params.size(); id++) {
            SearchCriteria searchCriteria = params.get(id);

            result = SearchOperation.getDataOption(searchCriteria.getOperation()) == SearchOperation.ALL
                    ? Specification.where(result).and(new ActivitySpecification(searchCriteria))
                    : Specification.where(result).or(new ActivitySpecification(searchCriteria));
        }
        return result;
    }
}

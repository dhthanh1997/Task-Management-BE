package com.ansv.taskmanagement.dto.specification;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.criteria.SearchOperation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class GenericSpecificationBuilder<T> {
    private final List<SearchCriteria> params;


    public GenericSpecificationBuilder(final List<SearchCriteria> params) {
        super();
        this.params = params;
    }

    public GenericSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final GenericSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key,operation,value));
        return this;
    }

    public final GenericSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<T> build() {
        if(params.size() == 0) {
            return null;
        }

        Specification<T> result = new GenericSpecification(params.get(0));

        for(int id = 1; id < params.size(); id ++) {
            SearchCriteria searchCriteria = params.get(id);

            result = SearchOperation.getDataOption(searchCriteria.getOperation()) == SearchOperation.ALL
                    ? Specification.where(result).and(new GenericSpecification<T>(searchCriteria))
                    : Specification.where(result).or(new GenericSpecification<T>(searchCriteria));
        }

        return result;
    }
}

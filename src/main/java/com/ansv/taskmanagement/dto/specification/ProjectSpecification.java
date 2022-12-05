package com.ansv.taskmanagement.dto.specification;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.criteria.SearchOperation;
import com.ansv.taskmanagement.model.Project;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.Objects;

public class ProjectSpecification implements Specification<Project> {

    private final SearchCriteria searchCriteria;

    public ProjectSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Specification<Project> and(Specification<Project> other) {

        return Specification.super.and(other);
    }

    @Override
    public Specification<Project> or(Specification<Project> other) {

        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS:
//                if (searchCriteria.getKey().equals("name")) {
//
//                }
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN:
//                if(searchCriteria.getKey().equals("name")) {
//                    return cb.notLike(cb.lower(root.get(searchCriteria.getKey())),"%" + strToSearch + "%" );
//                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch + "%");

            case BEGINS_WITH:
//                if(searchCriteria.getKey().equals("name")) {
//                    return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" = strToSearch + "%");
//                }
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), strToSearch + "%");
            case DOES_NOT_BEGIN_WITH:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), strToSearch + "%");
            case ENDS_WITH:
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch);
            case DOES_NOT_END_WITH:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch);
            case EQUAL:
                return cb.equal(cb.lower(root.get(searchCriteria.getKey())), strToSearch);
            case NOT_EQUAL:
                return cb.notEqual(cb.lower(root.get(searchCriteria.getKey())), strToSearch);
            case NUL:
                return cb.isNull(cb.lower(root.get(searchCriteria.getKey())));
            case NOT_NULL:
                return cb.isNotNull(cb.lower(root.get(searchCriteria.getKey())));
            case GREATER_THAN:
                return cb.greaterThan(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL:
                return cb.greaterThanOrEqualTo(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN:
                return cb.lessThan(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL:
                return cb.lessThanOrEqualTo(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        return null;
    }

    // các hàm join,....
}

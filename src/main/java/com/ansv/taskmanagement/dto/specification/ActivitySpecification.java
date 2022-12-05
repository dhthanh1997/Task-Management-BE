package com.ansv.taskmanagement.dto.specification;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.criteria.SearchOperation;
import com.ansv.taskmanagement.model.Activity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;


public class ActivitySpecification implements Specification<Activity> {

    private final SearchCriteria searchCriteria;

    public ActivitySpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Specification<Activity> and(Specification<Activity> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Activity> or(Specification<Activity> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();
        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS:
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch + "%");

            case BEGINS_WITH:

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
}

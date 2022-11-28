package com.yjw.sprint.tech.repository.specification;

import com.yjw.sprint.tech.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MemberSpecification {

    public static Specification<Member> searchMember(Map<String, Object> searchKey){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : searchKey.keySet()){
                // predicates.add(criteriaBuilder.equal(root.get(key), searchKey.get(key)));                // =
                predicates.add(criteriaBuilder.like(root.get(key), "%" + searchKey.get(key) + "%")); // like
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}

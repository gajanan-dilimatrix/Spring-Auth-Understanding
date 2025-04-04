package com.gajanan.Job.Posting.Application.repository.impl;

import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.repository.JobFilterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobFilterRepositoryImpl implements JobFilterRepository {

    private final EntityManager entityManager;



    @Override
    public List<Job> findJobsByTitle(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);
        Root<Job> root = cq.from(Job.class);
        Predicate jobPredicate = cb.equal(cb.lower(root.get("title")),title.toLowerCase());
        cq.select(root).where(jobPredicate);
        TypedQuery<Job>query=entityManager.createQuery(cq);
        return query.getResultList();

    }

    @Override
    public List<Job> findJobsByCompany(String company) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);
        Root<Job> root = cq.from(Job.class);
        Predicate jobPredicate = cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%");
        cq.select(root).where(jobPredicate);
        TypedQuery<Job>query=entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Job> findJobsByLocation(String location) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);
        Root<Job> root = cq.from(Job.class);
        Predicate jobPredicate = cb.equal(cb.lower(root.get("location")), location.toLowerCase());
        cq.select(root).where(jobPredicate);
        TypedQuery<Job>query=entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Job> findJobsByCompanyAndTitle(String company, String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);
        Root<Job> root = cq.from(Job.class);
        Predicate titlePredicate = cb.equal(cb.lower(root.get("title")), title.toLowerCase());
        Predicate comapnyPredicate = cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%");
        cq.select(root).where(cb.and(comapnyPredicate,titlePredicate));
        TypedQuery<Job>query=entityManager.createQuery(cq);
        return query.getResultList();
    }
}

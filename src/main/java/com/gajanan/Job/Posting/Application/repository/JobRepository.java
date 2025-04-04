package com.gajanan.Job.Posting.Application.repository;


import com.gajanan.Job.Posting.Application.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JobFilterRepository {

}

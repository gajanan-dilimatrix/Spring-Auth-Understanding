package com.gajanan.Job.Posting.Application.repository;

import com.gajanan.Job.Posting.Application.model.entity.Job;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface JobFilterRepository {
    List<Job> findJobsByTitle(String title);
    List<Job>findJobsByCompany(String company);
    List<Job>findJobsByLocation(String location);
    List<Job>findJobsByCompanyAndTitle(String company, String title);
}

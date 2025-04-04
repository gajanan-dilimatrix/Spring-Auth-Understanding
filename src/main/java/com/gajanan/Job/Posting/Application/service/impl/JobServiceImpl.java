package com.gajanan.Job.Posting.Application.service.impl;

import com.gajanan.Job.Posting.Application.config.aspect.NoLogging;
import com.gajanan.Job.Posting.Application.exception.JobNotFoundException;
import com.gajanan.Job.Posting.Application.mapper.RequestDTOConverter;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.dto.ResponseDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.repository.JobRepository;
import com.gajanan.Job.Posting.Application.service.JobService;
import com.gajanan.Job.Posting.Application.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public ResponseDTO createJob(RequestDTO requestDTO) {
        Job job = RequestDTOConverter.convertDTOtoJob(requestDTO);
        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_CREATED,jobRepository.save(job));
    }

    @Override
    @NoLogging
    public ResponseDTO getAllJobs() {
        List<Job> allJobs = jobRepository.findAll();
        if (allJobs.isEmpty()) {
            return new ResponseDTO(Util.STATUS_FAILED,Util.NO_JOBS_IN_PORTAL_MSG, Collections.emptyList());
        }
        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_RETRIEVED, allJobs);

    }

    @Override
    @Cacheable(value = "job", key = "#id")
    public ResponseDTO getJob(Long id) {
        Job job= jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(Util.JOB_NOT_FOUND_EXCEPTION_MSG));

        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_RETRIEVED, job);
    }

    @Override
    @CachePut(value = "job", key = "#id")
    public ResponseDTO updateJob(Long id, RequestDTO requestDTO) {
        Job toUpdatableJob = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(Util.JOB_NOT_FOUND_EXCEPTION_MSG));
        RequestDTOConverter.convertDTOtoJob(requestDTO, toUpdatableJob);
        Job updatedJob=jobRepository.save(toUpdatableJob);

        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_UPDATED, updatedJob);
    }

    @Override
    @CacheEvict(value = "job", key = "#id")
    public ResponseDTO deleteJob(Long id) {
        Job toDeletableJob = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(Util.JOB_NOT_FOUND_EXCEPTION_MSG));
        jobRepository.delete(toDeletableJob);

        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_DELETED, toDeletableJob);
    }

    @Override
    @NoLogging
    public ResponseDTO getJobsByTitle(String title) {
        List<Job> jobsByTitle = jobRepository.findJobsByTitle(title);
        if (jobsByTitle==null || jobsByTitle.isEmpty()) {
            return new ResponseDTO(Util.STATUS_FAILED,Util.NO_JOBS_IN_PORTAL_MSG, null);
        }
        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_RETRIEVED, jobsByTitle);
    }

    @Override
    @NoLogging
    public ResponseDTO getJobsByCompany(String company) {
       List<Job> jobsByCompany = jobRepository.findJobsByCompany(company);
       if (jobsByCompany.isEmpty()) {
           return new ResponseDTO(Util.STATUS_FAILED,Util.NO_JOBS_IN_PORTAL_MSG, null);
       }
       return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_RETRIEVED, jobsByCompany);
    }

    @Override
    @NoLogging
    public ResponseDTO getJobsByLocation(String location) {
        List<Job> jobsByLocation = jobRepository.findJobsByLocation(location);
        if (jobsByLocation.isEmpty()) {
            return new ResponseDTO(Util.STATUS_FAILED,Util.NO_JOBS_IN_PORTAL_MSG, null);
        }
        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_RETRIEVED, jobsByLocation);
    }

    @Override
    @NoLogging
    public ResponseDTO getJobsByCompanyAndTitle(String company, String title) {
        List<Job> jobsByCompanyAndTitle = jobRepository.findJobsByCompanyAndTitle(company, title);
        if (jobsByCompanyAndTitle.isEmpty()) {
            return new ResponseDTO(Util.STATUS_FAILED,Util.NO_JOBS_IN_PORTAL_MSG, null);
        }
        return new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_RETRIEVED, jobsByCompanyAndTitle);
    }

}

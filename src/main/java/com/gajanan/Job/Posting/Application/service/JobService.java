package com.gajanan.Job.Posting.Application.service;


import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.dto.ResponseDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;

import java.util.List;

public interface JobService {  // provides only method signatures for CRUD operations

    ResponseDTO createJob(RequestDTO requestDTO);

    ResponseDTO getAllJobs();

    ResponseDTO getJob(Long id);

    ResponseDTO updateJob(Long id, RequestDTO requestDTO);

    ResponseDTO deleteJob(Long id);

    ResponseDTO getJobsByTitle(String title);

    ResponseDTO getJobsByCompany(String company);

    ResponseDTO getJobsByCompanyAndTitle(String company, String title);

    ResponseDTO getJobsByLocation(String location);
}

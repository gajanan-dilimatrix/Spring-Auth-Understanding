package com.gajanan.Job.Posting.Application.mapper;

import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class RequestDTOConverter {

    public static Job convertDTOtoJob(RequestDTO requestDTO) {
        Job job = new Job();
        convertDTOtoJob(requestDTO, job);
        return job;
    }


    public static void convertDTOtoJob(RequestDTO requestDTO, Job toUpdatableJob) {
        toUpdatableJob.setTitle(requestDTO.getTitle());
        toUpdatableJob.setCompany(requestDTO.getCompany());
        toUpdatableJob.setDescription(requestDTO.getDescription());
        toUpdatableJob.setLocation(requestDTO.getLocation());
        toUpdatableJob.setSalary(requestDTO.getSalary());

    }
}

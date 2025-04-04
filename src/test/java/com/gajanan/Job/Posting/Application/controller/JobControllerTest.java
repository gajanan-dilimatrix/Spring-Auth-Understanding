package com.gajanan.Job.Posting.Application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gajanan.Job.Posting.Application.JobPostingApplication;
import com.gajanan.Job.Posting.Application.exception.JobNotFoundException;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.dto.ResponseDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.repository.JobRepository;
import com.gajanan.Job.Posting.Application.service.JobService;
import com.gajanan.Job.Posting.Application.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = JobPostingApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository jobRepository;

    @MockBean
    private JobService jobService;

    @Autowired
    private ObjectMapper objectMapper;

    private Job job;
    private RequestDTO requestDTO;

    @BeforeEach
    void setUp() {

        jobRepository.deleteAll();

        job = new Job();
        job.setId(1L);
        job.setTitle("Software Engineer");
        job.setCompany("Dilimatrix");
        job.setDescription("Develop software");
        job.setLocation("Sri Lanka");
        job.setSalary(60000.0);

        requestDTO = new RequestDTO();
        requestDTO.setTitle("Software Engineer");
        requestDTO.setCompany("Dilimatrix");
        requestDTO.setDescription("Develop software");
        requestDTO.setLocation("Sri Lanka");
        requestDTO.setSalary(60000.0);

    }

    @Test
    void createJob_ShouldReturnCreatedJob() throws Exception {
        when(jobService.createJob(any(RequestDTO.class))).thenReturn(new ResponseDTO(Util.STATUS_SUCCESS,Util.JOB_CREATED, job));

        mockMvc.perform(MockMvcRequestBuilders.post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Util.STATUS_SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Util.JOB_CREATED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(requestDTO.getTitle()));
    }

    @Test
    void createJob_ShouldReturnBadRequest_WhenRequestDTOIsInvalid() throws Exception {
        requestDTO.setTitle(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs() throws Exception {
        List<Job> jobList= Collections.singletonList(job);
        when(jobService.getAllJobs()).thenReturn(new ResponseDTO(Util.STATUS_SUCCESS, Util.JOB_RETRIEVED, jobList));
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Util.STATUS_SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Util.JOB_RETRIEVED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value(jobList.get(0).getTitle()));
    }

    @Test
    void getAllJobs_ShouldReturnEmptyList_WhenNoJobsExist() throws Exception{
        when(jobService.getAllJobs()).thenReturn(new ResponseDTO(Util.STATUS_FAILED,Util.NO_JOBS_IN_PORTAL_MSG, Collections.emptyList()));
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs")
                .contentType(MediaType.APPLICATION_JSON))
    .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Util.STATUS_FAILED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Util.NO_JOBS_IN_PORTAL_MSG))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }


    @Test
    void getJob_ShouldReturnJob_WhenJobExists() throws Exception {
        when(jobService.getJob(1L)).thenReturn(new ResponseDTO(Util.STATUS_SUCCESS, Util.JOB_RETRIEVED, job));
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs/" + job.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Util.STATUS_SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Util.JOB_RETRIEVED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(job.getTitle()));
    }
    @Test
    void getJob_ShouldReturnNotFound_WhenJobDoesNotExists() throws Exception {
        when(jobService.getJob(anyLong())).thenThrow(new JobNotFoundException(Util.JOB_NOT_FOUND_EXCEPTION_MSG));
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs/" + 99)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    void updateJob_ShouldReturnUpdatedJob() throws Exception {
        when(jobService.updateJob(anyLong(), any(RequestDTO.class))).thenReturn(new ResponseDTO(Util.STATUS_SUCCESS, Util.JOB_UPDATED, job));
        mockMvc.perform(MockMvcRequestBuilders.put("/jobs/" + job.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Util.STATUS_SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Util.JOB_UPDATED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(requestDTO.getTitle()));
    }


    @Test
    void deleteJob_ShouldReturnDeletedJob() throws Exception {
        when(jobService.deleteJob(anyLong())).thenReturn(new ResponseDTO(Util.STATUS_SUCCESS, Util.JOB_DELETED, job));
        mockMvc.perform(MockMvcRequestBuilders.delete("/jobs/" + job.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Util.STATUS_SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Util.JOB_DELETED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(job.getTitle()));


    }
}
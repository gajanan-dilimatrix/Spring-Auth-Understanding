package com.gajanan.Job.Posting.Application.service.impl;

import com.gajanan.Job.Posting.Application.exception.JobNotFoundException;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.dto.ResponseDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.repository.JobRepository;
import com.gajanan.Job.Posting.Application.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    //to avoid real database interactions
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    private Job job;
    private RequestDTO requestDTO;

    //this will run before each tests
    @BeforeEach
    void setUp() {
        job = new Job();
        job.setId(1L);
        job.setTitle("Software Engineer");
        job.setCompany("Dilimatrix");
        job.setDescription("Develop software");
        job.setLocation("Sri Lanka");
        job.setSalary(95000.0);


        requestDTO = new RequestDTO();
        requestDTO.setTitle("Software Engineer");
        requestDTO.setCompany("Dilimatrix");
        requestDTO.setDescription("Develop software");
        requestDTO.setLocation("Sri Lanka");
        requestDTO.setSalary(95000.0);
    }

    @Test
    void createJob_ShouldReturnSavedJob() {
        when(jobRepository.save(any(Job.class))).thenReturn(job);
        ResponseDTO response =jobService.createJob(requestDTO);
        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS,response.getStatus());
        assertEquals(Util.JOB_CREATED,response.getMessage());
        assertInstanceOf(Job.class, response.getData());
        Job savedJob=(Job) response.getData();
        assertEquals("Software Engineer", savedJob.getTitle());
        verify(jobRepository,times(1)).save(any(Job.class));
    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs_WhenJobsExist() {
    // mock data
        Job job2=new Job();
        job2.setId(1L);
        job2.setTitle("Data Engineer");
        job2.setCompany("Dilimatrix");
        job2.setDescription("designing, maintaining, and optimizing data infrastructure for data collection");
        job2.setLocation("Sri Lanka");
        job2.setSalary(105000.0);

        List<Job> jobList= Arrays.asList(job,job2);

        //when job exist in the database
        when(jobRepository.findAll()).thenReturn(jobList);
        ResponseDTO response = jobService.getAllJobs();

        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS, response.getStatus());
        assertEquals(Util.JOB_RETRIEVED, response.getMessage());
        assertInstanceOf(List.class, response.getData());

        Object data=response.getData();
        List<?> genericList = (List<?>) data;
        if (!genericList.isEmpty() && genericList.get(0) instanceof Job){
            List<Job> retrievedJobs=genericList.stream()
                            .filter(Job.class::isInstance)
                                    .map(Job.class::cast)
                                            .toList();

            assertEquals(2,retrievedJobs.size());
        }
        verify(jobRepository,times(1)).findAll();
    }

    @Test
    void getAllJobs_ShouldReturnFailureMessage_WhenNoJobsExist(){
        when(jobRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseDTO response = jobService.getAllJobs();

        assertNotNull(response);
        assertEquals(Util.STATUS_FAILED,response.getStatus());
        assertEquals(Util.NO_JOBS_IN_PORTAL_MSG,response.getMessage());
        assertEquals(Collections.emptyList(), response.getData());

        verify(jobRepository,times(1)).findAll();
    }

    @Test
    void getJob_ShouldReturnJob_WhenJobExists() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        ResponseDTO response = jobService.getJob(1L);
        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS,response.getStatus());
        assertEquals(Util.JOB_RETRIEVED,response.getMessage());
        assertInstanceOf(Job.class, response.getData());
        Job job1=(Job)response.getData();
        assertEquals("Software Engineer",job1.getTitle());
        verify(jobRepository,times(1)).findById(1L);
    }


    @Test
    void getJob_ShouldThrowException_WhenJobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());
        JobNotFoundException exception=  assertThrows(JobNotFoundException.class, () -> jobService.getJob(1L));
        assertEquals(Util.JOB_NOT_FOUND_EXCEPTION_MSG,exception.getMessage());
        verify(jobRepository,times(1)).findById(1L);
    }

    @Test
    void updateJob_ShouldReturnUpdatedJob() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        ResponseDTO response = jobService.updateJob(1L, requestDTO);
        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS,response.getStatus());
        assertEquals(Util.JOB_UPDATED,response.getMessage());
        assertInstanceOf(Job.class, response.getData());
        Job updatedJob = (Job) response.getData();
        assertEquals("Software Engineer", updatedJob.getTitle());
        verify(jobRepository,times(1)).findById(1L);
        verify(jobRepository,times(1)).save(any(Job.class));
    }

    @Test
    void updateJob_ShouldThrowException_WhenJobNotFound(){
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());
        JobNotFoundException exception= assertThrows(JobNotFoundException.class, () -> jobService.updateJob(1L,requestDTO));
        assertEquals(Util.JOB_NOT_FOUND_EXCEPTION_MSG,exception.getMessage());
        verify(jobRepository,times(1)).findById(1L);
    }

    @Test
    void deleteJob_ShouldRemoveJob_WhenJobExists() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        doNothing().when(jobRepository).delete(job);

        ResponseDTO response = jobService.deleteJob(1L);
        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS,response.getStatus());
        assertEquals(Util.JOB_DELETED,response.getMessage());
        assertInstanceOf(Job.class, response.getData());
        Job deletedJob = (Job) response.getData();
        assertEquals("Software Engineer", deletedJob.getTitle());
        verify(jobRepository,times(1)).findById(1L);
        verify(jobRepository,times(1)).delete(job);
    }

    @Test
    void deleteJob_ShouldThrowException_WhenJobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());
        JobNotFoundException exception= assertThrows(JobNotFoundException.class, () -> jobService.deleteJob(1L));
        assertEquals(Util.JOB_NOT_FOUND_EXCEPTION_MSG,exception.getMessage());
        verify(jobRepository,times(1)).findById(1L);
    }

    @Test
    void getJobsByTitle_ShouldReturnMatchingJobs_WhenJobsExist(){
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setCompany("Axiata Labs");
        job1.setDescription("Develop software");
        job1.setLocation("Sri Lanka");
        job1.setSalary(125000.0);

        List<Job> jobList= Arrays.asList(job,job1);

        when(jobRepository.findJobsByTitle("Software Engineer")).thenReturn(jobList);
        ResponseDTO response = jobService.getJobsByTitle("Software Engineer");

        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS, response.getStatus());
        assertEquals(Util.JOB_RETRIEVED, response.getMessage());
        assertInstanceOf(List.class, response.getData());

        Object data=response.getData();
        List<?> genericList = (List<?>) data;
        if (!genericList.isEmpty() && genericList.get(0) instanceof Job){
            List<Job> retrievedJobs=genericList.stream()
                    .filter(Job.class::isInstance)
                    .map(Job.class::cast)
                    .toList();

            assertEquals(2,retrievedJobs.size());
        }

        verify(jobRepository,times(1)).findJobsByTitle("Software Engineer");
    }

    @Test
    void getJobsByTitle_ShouldReturnEmptyList_WhenNoJobsExist(){
        when(jobRepository.findJobsByTitle("UI-UX Engineer")).thenReturn(Collections.emptyList());
        ResponseDTO emptyResponse = jobService.getJobsByTitle("UI-UX Engineer");

        assertNotNull(emptyResponse);
        assertEquals(Util.STATUS_FAILED, emptyResponse.getStatus());
        assertEquals(Util.NO_JOBS_IN_PORTAL_MSG, emptyResponse.getMessage());
        assertNull(emptyResponse.getData());

        verify(jobRepository,times(1)).findJobsByTitle("UI-UX Engineer");
    }

    @Test
    void getJobsByCompany_ShouldReturnMatchingJobs_WhenJobsExist(){
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setCompany("Dilimatrix");
        job1.setDescription("Develop software");
        job1.setLocation("Sri Lanka");
        job1.setSalary(125000.0);

        List<Job> jobList= Arrays.asList(job,job1);

        when(jobRepository.findJobsByCompany("Dilimatrix")).thenReturn(jobList);
        ResponseDTO response = jobService.getJobsByCompany("Dilimatrix");

        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS, response.getStatus());
        assertEquals(Util.JOB_RETRIEVED, response.getMessage());
        assertInstanceOf(List.class, response.getData());

        Object data=response.getData();
        List<?> genericList = (List<?>) data;
        if (!genericList.isEmpty() && genericList.get(0) instanceof Job){
            List<Job> retrievedJobs=genericList.stream()
                    .filter(Job.class::isInstance)
                    .map(Job.class::cast)
                    .toList();

            assertEquals(2,retrievedJobs.size());
        }

        verify(jobRepository,times(1)).findJobsByCompany("Dilimatrix");
    }

    @Test
    void getJobsByCompany_ShouldReturnEmptyList_WhenNoJobsExist(){
        when(jobRepository.findJobsByCompany("Axiata Labs")).thenReturn(Collections.emptyList());
        ResponseDTO emptyResponse = jobService.getJobsByCompany("Axiata Labs");

        assertNotNull(emptyResponse);
        assertEquals(Util.STATUS_FAILED, emptyResponse.getStatus());
        assertEquals(Util.NO_JOBS_IN_PORTAL_MSG, emptyResponse.getMessage());
        assertNull(emptyResponse.getData());

        verify(jobRepository,times(1)).findJobsByCompany("Axiata Labs");
    }

    @Test
    void getJobsByLocation_ShouldReturnMatchingJobs_WhenJobsExist(){
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setCompany("Dilimatrix");
        job1.setDescription("Develop software");
        job1.setLocation("Sri Lanka");
        job1.setSalary(125000.0);

        List<Job> jobList= Arrays.asList(job,job1);

        when(jobRepository.findJobsByLocation("Sri Lanka")).thenReturn(jobList);
        ResponseDTO response = jobService.getJobsByLocation("Sri Lanka");

        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS, response.getStatus());
        assertEquals(Util.JOB_RETRIEVED, response.getMessage());
        assertInstanceOf(List.class, response.getData());

        Object data=response.getData();
        List<?> genericList = (List<?>) data;
        if (!genericList.isEmpty() && genericList.get(0) instanceof Job){
            List<Job> retrievedJobs=genericList.stream()
                    .filter(Job.class::isInstance)
                    .map(Job.class::cast)
                    .toList();

            assertEquals(2,retrievedJobs.size());
        }

        verify(jobRepository,times(1)).findJobsByLocation("Sri Lanka");
    }

    @Test
    void getJobsByLocation_ShouldReturnEmptyList_WhenNoJobsExist(){
        when(jobRepository.findJobsByLocation("India")).thenReturn(Collections.emptyList());
        ResponseDTO emptyResponse = jobService.getJobsByLocation("India");

        assertNotNull(emptyResponse);
        assertEquals(Util.STATUS_FAILED, emptyResponse.getStatus());
        assertEquals(Util.NO_JOBS_IN_PORTAL_MSG, emptyResponse.getMessage());
        assertNull(emptyResponse.getData());

        verify(jobRepository,times(1)).findJobsByLocation("India");
    }

    @Test
    void getJobsByCompanyAndTitle_ShouldReturnMatchingJobs_WhenJobsExist(){
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setCompany("Dilimatrix");
        job1.setDescription("Develop software");
        job1.setLocation("Sri Lanka");
        job1.setSalary(125000.0);

        List<Job> jobList= Arrays.asList(job,job1);

        when(jobRepository.findJobsByCompanyAndTitle("Dilimatrix","Software Engineer")).thenReturn(jobList);
        ResponseDTO response = jobService.getJobsByCompanyAndTitle("Dilimatrix","Software Engineer");

        assertNotNull(response);
        assertEquals(Util.STATUS_SUCCESS, response.getStatus());
        assertEquals(Util.JOB_RETRIEVED, response.getMessage());
        assertInstanceOf(List.class, response.getData());

        Object data=response.getData();
        List<?> genericList = (List<?>) data;
        if (!genericList.isEmpty() && genericList.get(0) instanceof Job){
            List<Job> retrievedJobs=genericList.stream()
                    .filter(Job.class::isInstance)
                    .map(Job.class::cast)
                    .toList();

            assertEquals(2,retrievedJobs.size());
        }

        verify(jobRepository,times(1)).findJobsByCompanyAndTitle("Dilimatrix","Software Engineer");
    }

    @Test
    void getJobsByCompanyAndTitle_ShouldReturnEmptyList_WhenNoJobsExist(){
        when(jobRepository.findJobsByCompanyAndTitle("Axiata Labs","UI-UX Engineer")).thenReturn(Collections.emptyList());
        ResponseDTO emptyResponse = jobService.getJobsByCompanyAndTitle("Axiata Labs","UI-UX Engineer");

        assertNotNull(emptyResponse);
        assertEquals(Util.STATUS_FAILED, emptyResponse.getStatus());
        assertEquals(Util.NO_JOBS_IN_PORTAL_MSG, emptyResponse.getMessage());
        assertNull(emptyResponse.getData());

        verify(jobRepository,times(1)).findJobsByCompanyAndTitle("Axiata Labs","UI-UX Engineer");
    }
}
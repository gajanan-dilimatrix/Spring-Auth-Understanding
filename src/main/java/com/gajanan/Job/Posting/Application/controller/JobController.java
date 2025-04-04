package com.gajanan.Job.Posting.Application.controller;


import com.gajanan.Job.Posting.Application.config.aspect.NoLogging;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.dto.ResponseDTO;
import com.gajanan.Job.Posting.Application.service.CacheInspectionService;
import com.gajanan.Job.Posting.Application.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job Controller", description = "Endpoints for managing Jobs")
@Slf4j
@Validated
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final CacheInspectionService cacheInspectionService;

    @Operation(
            description = "POST endpoint for Job"
    )
    @PostMapping
    public ResponseEntity<ResponseDTO> createJob(@Valid @RequestBody RequestDTO requestDTO) {
        ResponseDTO responseDTO=jobService.createJob(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "GET endpoint for all Jobs"
    )
    @GetMapping
    @NoLogging
    public ResponseEntity<ResponseDTO> getAllJobs() {
        ResponseDTO responseDTO=jobService.getAllJobs();
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "GET endpoint for Job based on job's ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getJob(@PathVariable Long id) {
        ResponseDTO responseDTO=jobService.getJob(id);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "GET endpoint for Job based on Job Title"
    )
    @GetMapping("/title/{title}")
    @NoLogging
    public ResponseEntity<ResponseDTO> getJobsByTitle(@PathVariable String title) {
        ResponseDTO responseDTO=jobService.getJobsByTitle(title);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "GET endpoint for Job based on Company"
    )
    @GetMapping("/company/{company}")
    @NoLogging
    public ResponseEntity<ResponseDTO> getJobsByCompany(@PathVariable String company) {
        ResponseDTO responseDTO=jobService.getJobsByCompany(company);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "GET endpoint for Job based on Location"
    )
    @GetMapping("/location/{location}")
    @NoLogging
    public ResponseEntity<ResponseDTO> getJobsByLocation(@PathVariable String location) {
        ResponseDTO responseDTO=jobService.getJobsByLocation(location);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "GET endpoint for Job based on Company and Title"
    )
    @GetMapping("/title/{title}/company/{company}")
    @NoLogging
    public ResponseEntity<ResponseDTO> getJobsByCompanyAndTitle(@PathVariable String company,@PathVariable String title) {
        ResponseDTO responseDTO=jobService.getJobsByCompanyAndTitle(company, title);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "PUT endpoint for Job"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateJob(@PathVariable Long id, @Valid @RequestBody RequestDTO requestDTO) {
        ResponseDTO responseDTO=jobService.updateJob(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            description = "DELETE endpoint for Job"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteJob(@PathVariable Long id) {
        ResponseDTO responseDTO=jobService.deleteJob(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/cacheData")
    public void getCacheData(){
        cacheInspectionService.printCacheContent("job");
    }

}

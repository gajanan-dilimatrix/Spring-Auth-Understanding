package com.gajanan.Job.Posting.Application.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    // messages
    public static final String NO_JOBS_IN_PORTAL_MSG="Currently, there are no job postings available. Please check back later.";
    public static final String JOB_CREATED="Job Created Successfully";
    public static final String JOB_RETRIEVED="Job Retrieved Successfully";
    public static final String JOB_UPDATED="Job Updated Successfully";
    public static final String JOB_DELETED="Job Deleted Successfully";

    // status
    public static final String STATUS_FAILED="failed";
    public static final String STATUS_SUCCESS="success";

    // custom exception message
    public static final String JOB_NOT_FOUND_EXCEPTION_MSG="JOB_NOT_FOUND_EXCEPTION";
}

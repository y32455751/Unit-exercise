package com.example.unit3.Unit_3_2_3.Serialization.api.entity;

import java.io.Serializable;

public class Job implements Serializable {

    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}

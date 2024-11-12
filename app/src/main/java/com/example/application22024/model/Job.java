package com.example.application22024.model;


public class Job {
    private String jobName;
    private String employerName;
    private String location;
    private String jobType;
    private String workplaceType;
    private String duration;
    private String salary;
    private String durationType;

    public Job(String jobName, String employerName, String location, String jobType, String workplaceType, String duration, String salary, String durationType) {
        this.jobName = jobName;
        this.employerName = employerName;
        this.location = location;
        this.jobType = jobType;
        this.workplaceType = workplaceType;
        this.duration = duration;
        this.salary = salary;
        this.durationType = durationType;
    }




    // Getters
    public String getJobName() {
        return jobName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getLocation() {
        return location;
    }

    public String getJobType() {
        return jobType;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public String getDuration() {
        return duration;
    }

    public String getSalary() {
        return salary;
    }

    public String getDurationType() {
        return durationType;
    }

    // Setters
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }
}

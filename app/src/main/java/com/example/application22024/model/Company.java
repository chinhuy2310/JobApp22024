package com.example.application22024.model;

import java.util.ArrayList;
import java.util.List;
public class Company {
    private String companyName;
    private String city;
    private int jobPositions;
    private boolean isExpanded = false; // Trạng thái mở rộng
    private List<Job> jobs; // Danh sách bài đăng tuyển

    // Updated Constructor
    public Company(String companyName, String city, int jobPositions, List<Job> jobs) {
        this.companyName = companyName;
        this.city = city;
        this.jobPositions = jobPositions;
        this.jobs = jobs != null ? jobs : new ArrayList<>(); // Use passed jobs list or initialize with empty list
    }

    // Getter and Setter methods

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs != null ? jobs : new ArrayList<>(); // Avoid null jobs list
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getJobPositions() {
        return jobPositions;
    }

    public void setJobPositions(int jobPositions) {
        this.jobPositions = jobPositions;
    }
}

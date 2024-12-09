package com.example.application22024.model;

import java.util.ArrayList;
import java.util.List;

public class Company {


    private int company_id;
    private String company_name;
    private String name_of_representative;
    private String address;
    private String contact;
    private String registration_number;
    private String company_image;
    private int job_count;  // Trường mới cho số lượng bài đăng
    private int total_applicants;
    private boolean isExpanded = false; // Trạng thái mở rộng
    private List<Job> jobs = new ArrayList<>();
    public Company(int company_id, String company_name, String name_of_representative, String address, String contact, String registration_number,String company_image, int job_count, int total_applicants) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.name_of_representative = name_of_representative;
        this.address = address;
        this.contact = contact;
        this.registration_number = registration_number;
        this.company_image = company_image;
        this.job_count = job_count;
        this.total_applicants = total_applicants;
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
        this.jobs = jobs != null ? jobs : new ArrayList<>(); // Safeguard for null
    }

    // Getter và setter
    public int getCompanyId() {
        return company_id;
    }

    public void setCompanyId(int companyId) {
        this.company_id = companyId;
    }

    public String getCompanyName() {
        return company_name;
    }

    public void setCompanyName(String companyName) {
        this.company_name = companyName;
    }

    public String getNameOfRepresentative() {
        return name_of_representative;
    }

    public void setNameOfRepresentative(String nameOfRepresentative) {
        this.name_of_representative = nameOfRepresentative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getRegistrationNumber() {
        return registration_number;
    }
    public void setRegistrationNumber(String registration_number) {
        this.registration_number = registration_number;
    }
    public String getCompanyIamge() {
        return company_image;
    }
    public void setCompanyIamge(String company_image) {
        this.company_image = company_image;
    }
    public int getJobCount() {
        return job_count;
    }

    public void setJobCount(int jobCount) {
        this.job_count = jobCount;
    }
    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + company_id +
                ", companyName='" + company_name + '\'' +
                ", nameOfRepresentative='" + name_of_representative + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", jobCount=" + job_count +
                ", registerNumber='" + registration_number + '\'' +
                ", isExpanded=" + isExpanded +
                '}';
    }


    public int getTotal_applicants() {
        return total_applicants;
    }

    public void setTotal_applicants(int total_applicants) {
        this.total_applicants = total_applicants;
    }
}

package com.example.application22024.model;

public class Job {
    private int job_id;
    private int company_id;
    private String PostDate;
    private String Title;
    private Integer Salary;
    private String SalaryType;
    private String WorkHoursStart;
    private String WorkHoursEnd;
    private String can_negotiable_time;
    private String RecruitmentEnd;
    private String RecruitmentGender;
    private Integer RecruitmentCount;
    private String WorkType;
    private String WorkField;
    private String WorkDays;
    private String WorkPeriod;
    private String can_negotiable_days;
    private int num_applicants;
    private String Details;
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // Getters and Setters
    public int getJobId() {
        return job_id;
    }
    public void setJobId(int jobId) {
        this.job_id = jobId;
    }

    public int getCompanyId() {
        return company_id;
    }
    public void setCompanyId(int companyId) {
        this.company_id = companyId;
    }

    public String getPostDate() {
        return PostDate;
    }
    public void setPostDate(String postDate) {
        this.PostDate = postDate;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        this.Title = title;
    }

    public Integer getSalary() {
        return Salary;
    }
    public void setSalary(Integer salary) {
        this.Salary = salary;
    }

    public String getSalaryType() {
        return SalaryType;
    }
    public void setSalaryType(String SalaryType) {
        this.SalaryType = SalaryType;
    }

    public String getWorkHoursStart() {
        return WorkHoursStart;
    }
    public void setWorkHoursStart(String workHoursStart) {
        this.WorkHoursStart = workHoursStart;
    }

    public String getWorkHoursEnd() {
        return WorkHoursEnd;
    }
    public void setWorkHoursEnd(String workHoursEnd) {
        this.WorkHoursEnd = workHoursEnd;
    }

    public String getCanNegotiableTime() {
        return can_negotiable_time;
    }
    public void setCanNegotiableTime(String canNegotiableTime) {
        this.can_negotiable_time = canNegotiableTime;
    }

    public String getRecruitmentEnd() {
        return RecruitmentEnd;
    }
    public void setRecruitmentEnd(String recruitmentEnd) {
        this.RecruitmentEnd = recruitmentEnd;
    }

    public String getRecruitmentGender() {
        return RecruitmentGender;
    }
    public void setRecruitmentGender(String recruitmentGender) {
        this.RecruitmentGender = recruitmentGender;
    }

    public Integer getRecruitmentCount() {
        return RecruitmentCount;
    }
    public void setRecruitmentCount(Integer recruitmentCount) {
        this.RecruitmentCount = recruitmentCount;
    }

    public String getWorkType() {
        return WorkType;
    }
    public void setWorkType(String workType) {
        this.WorkType = workType;
    }

    public String getWorkField() {
        return WorkField;
    }
    public void setWorkField(String workField) {
        this.WorkField = workField;
    }

    public String getWorkDays() {
        return WorkDays;
    }
    public void setWorkDays(String workDays) {
        this.WorkDays = workDays;
    }

    public String getWorkPeriod() {
        return WorkPeriod;
    }
    public void setWorkPeriod(String workPeriod) {
        this.WorkPeriod = workPeriod;
    }

    public String getCanNegotiableDays() {
        return can_negotiable_days;
    }
    public void setCanNegotiableDays(String canNegotiableDays) {
        this.can_negotiable_days = canNegotiableDays;
    }

    public String getDetails() {
        return Details;
    }
    public void setDetails(String details) {
        this.Details = details;
    }

    public int getNum_applicants() {
        return num_applicants;
    }

    public void setNum_applicants(int num_applicants) {
        this.num_applicants = num_applicants;
    }
}

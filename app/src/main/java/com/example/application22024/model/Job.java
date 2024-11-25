//package com.example.application22024.model;
//
//public class Job {
//    private int jobID;               // ID công việc
//    private int companyID;           // ID công ty
//    private String title;            // Tiêu đề công việc
//    private String companyName;      // Tên công ty
//    private int salary;              // Lương
//    private String workDays;         // Số ngày làm việc trong tuần
//    private String workHoursStart;   // Thời gian bắt đầu làm việc
//    private String workHoursEnd;     // Thời gian kết thúc làm việc
//    private String recruitmentEnd;   // Ngày kết thúc tuyển dụng
//    private String recruitmentGender; // Giới tính yêu cầu
//    private int recruitmentCount;    // Số lượng tuyển dụng
//    private String workType;         // Loại hình công việc
//    private String workPeriod;       // Thời gian làm việc
//    private String workLocation;     // Địa điểm làm việc
//    private String details;          // Chi tiết công việc
//    private String companyContact;   // Số điện thoại công ty
//
//    public Job(String jobName, String employerName, String location, String jobType, String workplaceType, String duration, int salary, String durationType) {
//        this.title = jobName;
//        this.companyName = employerName;
//        this.workLocation = location;
//        this.workType = jobType;
//        this.workLocation = workplaceType;
//        this.workPeriod = duration;
//        this.salary = salary;
//        this.workType = durationType;
//    }
//    // Getter và Setter
//    public int getJobID() {
//        return jobID;
//    }
//
//    public void setJobID(int jobID) {
//        this.jobID = jobID;
//    }
//
//    public int getCompanyID() {
//        return companyID;
//    }
//
//    public void setCompanyID(int companyID) {
//        this.companyID = companyID;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//
//    public int getSalary() {
//        return salary;
//    }
//
//    public void setSalary(int salary) {
//        this.salary = salary;
//    }
//
//    public String getWorkDays() {
//        return workDays;
//    }
//
//    public void setWorkDays(String workDays) {
//        this.workDays = workDays;
//    }
//
//    public String getWorkHoursStart() {
//        return workHoursStart;
//    }
//
//    public void setWorkHoursStart(String workHoursStart) {
//        this.workHoursStart = workHoursStart;
//    }
//
//    public String getWorkHoursEnd() {
//        return workHoursEnd;
//    }
//
//    public void setWorkHoursEnd(String workHoursEnd) {
//        this.workHoursEnd = workHoursEnd;
//    }
//
//    public String getRecruitmentEnd() {
//        return recruitmentEnd;
//    }
//
//    public void setRecruitmentEnd(String recruitmentEnd) {
//        this.recruitmentEnd = recruitmentEnd;
//    }
//
//    public String getRecruitmentGender() {
//        return recruitmentGender;
//    }
//
//    public void setRecruitmentGender(String recruitmentGender) {
//        this.recruitmentGender = recruitmentGender;
//    }
//
//    public int getRecruitmentCount() {
//        return recruitmentCount;
//    }
//
//    public void setRecruitmentCount(int recruitmentCount) {
//        this.recruitmentCount = recruitmentCount;
//    }
//
//    public String getWorkType() {
//        return workType;
//    }
//
//    public void setWorkType(String workType) {
//        this.workType = workType;
//    }
//
//    public String getWorkPeriod() {
//        return workPeriod;
//    }
//
//    public void setWorkPeriod(String workPeriod) {
//        this.workPeriod = workPeriod;
//    }
//
//    public String getWorkLocation() {
//        return workLocation;
//    }
//
//    public void setWorkLocation(String workLocation) {
//        this.workLocation = workLocation;
//    }
//
//    public String getDetails() {
//        return details;
//    }
//
//    public void setDetails(String details) {
//        this.details = details;
//    }
//
//    public String getCompanyContact() {
//        return companyContact;
//    }
//
//    public void setCompanyContact(String companyContact) {
//        this.companyContact = companyContact;
//    }
//}
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
    // Constructor cho trường hợp chỉ lấy 3 cột (jobTitle, location, salary)
    public Job(String jobTitle, String location, String salary) {
        this.jobName = jobTitle;
        this.location = location;
        this.salary = salary;
        // Các trường khác có thể để null hoặc mặc định
        this.employerName = "";
        this.jobType = "";
        this.duration = "";
        this.workplaceType = "";
        this.durationType = "";
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
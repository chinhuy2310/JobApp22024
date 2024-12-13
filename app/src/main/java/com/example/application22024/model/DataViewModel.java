package com.example.application22024.model;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {

    private Company selectedCompany;
    private Job selectedJob;
    private CompanyJobItem selectedCompanyJobItem;
    private MutableLiveData<Applicant> selectedApplicant = new MutableLiveData<>();

    // Các trường dữ liệu cần thu thập từ người dùng
    private String recruitmentTitle, companyName, contact, selectedRecruitmentField, otherRecruitmentField, selectedGender;
    private String recruitmentCount, selectedSalaryType, salary, startTime, endTime, workType, workPeriod, workDay, recruitmentEndTime;
    private boolean isOption1Checked;
    private boolean isOption2Checked;
    private boolean isOption3Checked;
    private String address, detailAddress, description;
    private String representativeName, registerNumber;
    private Uri selectedImageUri;
    //    public RegistrationViewModel(String recruitmentContent, int selectedPosition, int selectedGenderPosition, String startTime, String endTime, String workArrangement, String salaryType, String partOfDay1, String partOfDay2) {
//    }
//    public RegistrationViewModel() {
//        // Khởi tạo các giá trị mặc định nếu cần
//        recruitmentContent = "";
//        selectedPosition = -1;
//        selectedGenderPosition = -1;
//        startTime = "";
//        endTime = "";
//        workArrangement = "";
//        salaryType = "";
//        partOfDay1 = "";
//        partOfDay2 = "";
//        selectedGender = "";
//        selectedRecruitmentField = "";
//        otherRecruitmentField = "";
//    }
    // Getter và Setter cho các trường

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company company) {
        this.selectedCompany = company;
    }

    public Job getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(Job job) {
        this.selectedJob = job;
    }

    // step1
    public String getRecruitmentTitle() {
        return recruitmentTitle;
    }

    public void setRecruitmentTitle(String recruitmentTitle) {
        this.recruitmentTitle = recruitmentTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSelectedRecruitmentField() {

        return selectedRecruitmentField;
    }

    public void setSelectedRecruitmentField(String gender) {
        this.selectedRecruitmentField = gender;
    }

    public String getOtherRecruitmentField() {

        return otherRecruitmentField;
    }

    public void setOtherRecruitmentField(String otherRecruitmentField) {
        this.otherRecruitmentField = otherRecruitmentField;
    }

    public String getSelectedGender() {

        return selectedGender;
    }

    public void setSelectedGender(String gender) {
        this.selectedGender = gender;
    }

    //step2
    public String getRecruitmentCount() {
        return recruitmentCount;
    }

    public void setRecruitmentCount(String recruitmentCount) {
        this.recruitmentCount = recruitmentCount;
    }

    public String getSelectedSalaryType() {
        return selectedSalaryType;
    }

    public void setSelectedSalaryType(String salaryType) {
        this.selectedSalaryType = salaryType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(String workPeriod) {
        this.workPeriod = workPeriod;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getRecruitmentEndTime() {
        return recruitmentEndTime;
    }

    public void setRecruitmentEndTime(String recruitmentEndTime) {
        this.recruitmentEndTime = recruitmentEndTime;
    }


    public String isOption1Checked() {
        return isOption1Checked ? "Yes" : "No";
    }

    public String isOption2Checked() {
        return isOption2Checked ? "Yes" : "No";
    }

    public String isOption3Checked() {
        return isOption3Checked ? "Yes" : "No";
    }

    public void setOption1Checked(boolean option1Checked) {
        this.isOption1Checked = option1Checked;
    }

    public void setOption2Checked(boolean option2Checked) {
        this.isOption2Checked = option2Checked;
    }

    public void setOption3Checked(boolean option3Checked) {
        this.isOption3Checked = option3Checked;
    }

    //step3
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //step4
    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }


    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Uri getSelectedImageUri() {
        return selectedImageUri;
    }

    public void setSelectedImageUri(Uri selectedImageUri) {
        this.selectedImageUri = selectedImageUri;
    }

    public void reset() {
        // Xóa dữ liệu trong viewModel
        selectedCompany = null;
        selectedJob = null;
        recruitmentTitle = null;
        companyName = null;
        contact = null;
        selectedRecruitmentField = null;
        otherRecruitmentField = null;
        selectedGender = null;
        recruitmentCount = null;
        selectedSalaryType = null;
        salary = null;
        startTime = null;
        endTime = null;
        workType = null;
        workPeriod = null;
        workDay = null;
        recruitmentEndTime = null;
        isOption1Checked = false;
        isOption2Checked = false;
        isOption3Checked = false;
        address = null;
        detailAddress = null;
        description = null;
        representativeName = null;
        registerNumber = null;
        selectedImageUri = null;
        selectedCompanyJobItem = null;
        selectedApplicant = new MutableLiveData<>();

    }


    public CompanyJobItem getSelectedCompanyJobItem() {
        return selectedCompanyJobItem;
    }

    public void setSelectedCompanyJobItem(CompanyJobItem selectedCompanyJobItem) {
        this.selectedCompanyJobItem = selectedCompanyJobItem;
    }
    public LiveData<Applicant> getSelectedApplicant() {
        return selectedApplicant;
    }

    public void setSelectedApplicant(Applicant applicant) {
        selectedApplicant.setValue(applicant);
    }
}

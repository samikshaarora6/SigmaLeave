package com.example.sigmaleave;

public class Leaves {

    String reason;
    Integer leaveId;
    String startDate;
    String endDate;
    Boolean ManagerApproved;
    Boolean AdminApproval;

    public Boolean isAdminApproval() {
        return AdminApproval;
    }

    public void setAdminApproval(Boolean adminApproval) {
        AdminApproval = adminApproval;
    }

    Integer empId;
    String LeaveType;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Leaves(String reason, Integer leaveId, String startDate, String endDate, boolean managerApproved, Boolean adminApproval, int empId, String leaveType) {
        this.reason = reason;
        this.leaveId = leaveId;
        this.startDate = startDate;
        this.endDate = endDate;
        ManagerApproved = managerApproved;
        AdminApproval = adminApproval;
        this.empId = empId;
        LeaveType = leaveType;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Boolean isManagerApproved() {
        return ManagerApproved;
    }

    public void setManagerApproved(boolean managerApproved) {
        ManagerApproved = managerApproved;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean isStatus() {
        return ManagerApproved;
    }

    public void setStatus(boolean status) {
        this.ManagerApproved = status;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Leaves() {
    }

    public String getLeaveType() {
        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }

}

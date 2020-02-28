package com.example.sigmaleave;

public class Leaves {

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Leaves(String reason, String leaveId, String startDate, String endDate, boolean status, int empId, String leaveType) {
        this.reason = reason;
        this.leaveId = leaveId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.empId = empId;
        LeaveType = leaveType;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
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

    String reason;
    String leaveId;
    String startDate;
    String endDate;
    boolean status;
    int empId;
    String LeaveType;
}

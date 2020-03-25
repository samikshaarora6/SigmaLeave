package com.example.sigmaleave;

public class Leaves {

    String reason;
    Integer leaveId;
    String startDate;
    String endDate;
    boolean ManagerApproved;
    boolean AdminApproval;
    String m_ID;
    int no_of_leaves;
    int no_of_chunks;

    public int getNo_of_leaves() {
        return no_of_leaves;
    }

    public void setNo_of_leaves(int no_of_leaves) {
        this.no_of_leaves = no_of_leaves;
    }

    public int getNo_of_chunks() {
        return no_of_chunks;
    }

    public void setNo_of_chunks(int no_of_chunks) {
        this.no_of_chunks = no_of_chunks;
    }


    Integer empId;
    String LeaveType;

    public String getM_ID() {
        return m_ID;
    }

    public void setM_ID(String m_ID) {
        this.m_ID = m_ID;
    }

    public Leaves(String reason, Integer leaveId, String startDate, String endDate, boolean managerApproved, boolean adminApproval, String m_ID, Integer empId, String leaveType, int No_of_chunks,int No_of_leaves) {
        this.reason = reason;
        this.leaveId = leaveId;
        this.startDate = startDate;
        this.endDate = endDate;
        ManagerApproved = managerApproved;
        AdminApproval = adminApproval;
        this.m_ID = m_ID;
        this.empId = empId;
        LeaveType = leaveType;
        no_of_chunks=No_of_chunks;
        no_of_leaves=No_of_leaves;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
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

    public boolean isManagerApproved() {
        return ManagerApproved;
    }

    public void setManagerApproved(boolean managerApproved) {
        ManagerApproved = managerApproved;
    }

    public boolean isAdminApproval() {
        return AdminApproval;
    }

    public Leaves() {

    }

    public void setAdminApproval(boolean adminApproval) {
        AdminApproval = adminApproval;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getLeaveType() {
        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }
}

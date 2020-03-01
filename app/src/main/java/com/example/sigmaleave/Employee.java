package com.example.sigmaleave;

public class Employee {
    String name;
    String email;
    String password;
    int no_of_leaves;
    int no_of_chunks;
    int E_ID;
    String DOB;
    String Maritial_Status;
    String MobileNumber;
    String ManagerName;

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }
    String BloodGroup;
    public Employee(String name, String email, String password,
                    String DOB, String maritial_Status, String mobileNumber, String managerName, String bloodGroup) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.DOB = DOB;
        Maritial_Status = maritial_Status;
        MobileNumber = mobileNumber;
        ManagerName = managerName;
        BloodGroup=bloodGroup;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getMaritial_Status() {
        return Maritial_Status;
    }

    public void setMaritial_Status(String maritial_Status) {
        Maritial_Status = maritial_Status;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }


    public int getE_ID() {
        return E_ID;
    }

    public void setE_ID(int e_ID) {
        E_ID = e_ID;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

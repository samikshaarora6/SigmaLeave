package com.example.sigmaleave;

public class Employee {
    String name;
    String email;
    String password;
    int no_of_leaves;
    int no_of_chunks;
    int E_ID;

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

    public Employee(String name, String email, String password) {
        this.name = name;
        //this.designation = designation;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public String getDesignation() {
    /// return designation;
    //}
//
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

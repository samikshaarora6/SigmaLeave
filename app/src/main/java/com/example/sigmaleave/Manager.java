package com.example.sigmaleave;

public class Manager {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getM_ID() {
        return M_ID;
    }

    public void setM_ID(int m_ID) {
        M_ID = m_ID;
    }

    public Manager() {
    }

    public Manager(String name, String email, String password, int no_of_leaves, int no_of_chunks, int m_ID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.no_of_leaves = no_of_leaves;
        this.no_of_chunks = no_of_chunks;
        M_ID = m_ID;
    }

    String name;
    String email;
    String password;
    int no_of_leaves;
    int no_of_chunks;
    int M_ID;
}
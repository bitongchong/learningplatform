package com.sicau.platform.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = 6L;

    @Id
    Long adminId;
    String job;
    String department;
    String account;
    String password;

    public Admin() {
    }

    public Long getAdminId() {
        return this.adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Admin(adminId=" + this.getAdminId() + ", job=" + this.getJob() + ", department=" + this.getDepartment() + ", account=" + this.getAccount() + ", password=" + this.getPassword() + ")";
    }
}

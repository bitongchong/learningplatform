package com.sicau.platform.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "login_ticket")
public class LoginTicket implements Serializable {
	private static final long serialVersionUID = 5L;
	@Id
	private Long id;
	private Long userId;
	private String ticket;
	private Date expired;
	private int status;

	public LoginTicket() {
	}

	public Long getId() {
		return this.id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public String getTicket() {
		return this.ticket;
	}

	public Date getExpired() {
		return this.expired;
	}

	public int getStatus() {
		return this.status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		return "LoginTicket(id=" + this.getId() + ", userId=" + this.getUserId() + ", ticket=" + this.getTicket() + ", expired=" + this.getExpired() + ", status=" + this.getStatus() + ")";
	}
}

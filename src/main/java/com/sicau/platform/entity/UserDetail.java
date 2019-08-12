package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_detail")
public class UserDetail implements Serializable {
    private static final long serialVersionUID = 2L;
    @Id
    private Long detailid;
    private Long sid;
    private String name;
    private String sclass;
    private String phone;
    private String email;
    private String identity;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date applicationTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date activistTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date potentialTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date probationaryTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date fullpartyTime;
}

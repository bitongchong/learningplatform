package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
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


    public UserDetail() {
    }

    public Long getDetailid() {
        return this.detailid;
    }

    public Long getSid() {
        return this.sid;
    }

    public String getSclass() {
        return this.sclass;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getIdentity() {
        return this.identity;
    }

    public String getName() {
        return this.name;
    }

    public Date getApplicationTime() {
        return this.applicationTime;
    }

    public Date getActivistTime() {
        return this.activistTime;
    }

    public Date getPotentialTime() {
        return this.potentialTime;
    }

    public Date getProbationaryTime() {
        return this.probationaryTime;
    }

    public Date getFullpartyTime() {
        return this.fullpartyTime;
    }

    public void setDetailid(Long detailid) {
        this.detailid = detailid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public void setActivistTime(Date activistTime) {
        this.activistTime = activistTime;
    }

    public void setPotentialTime(Date potentialTime) {
        this.potentialTime = potentialTime;
    }

    public void setProbationaryTime(Date probationaryTime) {
        this.probationaryTime = probationaryTime;
    }

    public void setFullpartyTime(Date fullpartyTime) {
        this.fullpartyTime = fullpartyTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDetail)) return false;
        final UserDetail other = (UserDetail) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$detailid = this.getDetailid();
        final Object other$detailid = other.getDetailid();
        if (this$detailid == null ? other$detailid != null : !this$detailid.equals(other$detailid)) return false;
        final Object this$sid = this.getSid();
        final Object other$sid = other.getSid();
        if (this$sid == null ? other$sid != null : !this$sid.equals(other$sid)) return false;
        final Object this$sclass = this.getSclass();
        final Object other$sclass = other.getSclass();
        if (this$sclass == null ? other$sclass != null : !this$sclass.equals(other$sclass)) return false;
        final Object this$phone = this.getPhone();
        final Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$identity = this.getIdentity();
        final Object other$identity = other.getIdentity();
        if (this$identity == null ? other$identity != null : !this$identity.equals(other$identity)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$applicationTime = this.getApplicationTime();
        final Object other$applicationTime = other.getApplicationTime();
        if (this$applicationTime == null ? other$applicationTime != null : !this$applicationTime.equals(other$applicationTime))
            return false;
        final Object this$activistTime = this.getActivistTime();
        final Object other$activistTime = other.getActivistTime();
        if (this$activistTime == null ? other$activistTime != null : !this$activistTime.equals(other$activistTime))
            return false;
        final Object this$potentialTime = this.getPotentialTime();
        final Object other$potentialTime = other.getPotentialTime();
        if (this$potentialTime == null ? other$potentialTime != null : !this$potentialTime.equals(other$potentialTime))
            return false;
        final Object this$probationaryTime = this.getProbationaryTime();
        final Object other$probationaryTime = other.getProbationaryTime();
        if (this$probationaryTime == null ? other$probationaryTime != null : !this$probationaryTime.equals(other$probationaryTime))
            return false;
        final Object this$fullpartyTime = this.getFullpartyTime();
        final Object other$fullpartyTime = other.getFullpartyTime();
        if (this$fullpartyTime == null ? other$fullpartyTime != null : !this$fullpartyTime.equals(other$fullpartyTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserDetail;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $detailid = this.getDetailid();
        result = result * PRIME + ($detailid == null ? 43 : $detailid.hashCode());
        final Object $sid = this.getSid();
        result = result * PRIME + ($sid == null ? 43 : $sid.hashCode());
        final Object $sclass = this.getSclass();
        result = result * PRIME + ($sclass == null ? 43 : $sclass.hashCode());
        final Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $identity = this.getIdentity();
        result = result * PRIME + ($identity == null ? 43 : $identity.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $applicationTime = this.getApplicationTime();
        result = result * PRIME + ($applicationTime == null ? 43 : $applicationTime.hashCode());
        final Object $activistTime = this.getActivistTime();
        result = result * PRIME + ($activistTime == null ? 43 : $activistTime.hashCode());
        final Object $potentialTime = this.getPotentialTime();
        result = result * PRIME + ($potentialTime == null ? 43 : $potentialTime.hashCode());
        final Object $probationaryTime = this.getProbationaryTime();
        result = result * PRIME + ($probationaryTime == null ? 43 : $probationaryTime.hashCode());
        final Object $fullpartyTime = this.getFullpartyTime();
        result = result * PRIME + ($fullpartyTime == null ? 43 : $fullpartyTime.hashCode());
        return result;
    }

    public String toString() {
        return "UserDetail(detailid=" + this.getDetailid() + ", sid=" + this.getSid() + ", sclass=" + this.getSclass() + ", phone=" + this.getPhone() + ", email=" + this.getEmail() + ", identity=" + this.getIdentity() + ", name=" + this.getName() + ", applicationTime=" + this.getApplicationTime() + ", activistTime=" + this.getActivistTime() + ", potentialTime=" + this.getPotentialTime() + ", probationaryTime=" + this.getProbationaryTime() + ", fullpartyTime=" + this.getFullpartyTime() + ")";
    }
}

package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liuyuehe
 * @description 打卡记录
 * @date 2019/8/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "punch_in_record")
public class PunchInRecord {
    @Id
    private Long recordId;
    private Long userId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date punchInTime;
    private Integer status;
}

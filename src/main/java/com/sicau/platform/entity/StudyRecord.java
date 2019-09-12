package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liuyuehe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "study_record")
public class StudyRecord  implements Serializable {
    @Id
    private Long recordId;
    private Long articleId;
    private String articleTitle;
    private Long userId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date openTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accomplishTime;
    private Integer status;
    private String userName;
}

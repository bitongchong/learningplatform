package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author boot liu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "questionnaire")
public class Questionnaire {
    @Id
    private Long qid;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String url;
    private Long userId;
    private String title;
}

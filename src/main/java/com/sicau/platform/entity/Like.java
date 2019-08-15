package com.sicau.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "like_record")
public class Like {
    @Id
    private Long likeId;
    private Long userId;
    private Long articleId;
    private Date createTime;
}

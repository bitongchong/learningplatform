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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "collection_record")
public class CollectionRecord {
    @Id
    private Long collectionId;
    private Long articleId;
    private Date createTime;
    private Long userId;
}

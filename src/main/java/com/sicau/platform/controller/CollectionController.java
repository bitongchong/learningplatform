package com.sicau.platform.controller;

import com.sicau.platform.entity.CollectionRecord;
import com.sicau.platform.entity.PageResult;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.service.CollectionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/15
 */
@RestController
public class CollectionController {
    @Autowired
    CollectionRecordService collectionRecordService;

    @PostMapping("/addCollection")
    public Result addCollection(CollectionRecord collectionRecord) {
        collectionRecordService.addCollectionRecord(collectionRecord);
        return new Result(true, StatusCode.OK, "收藏成功");
    }

    @GetMapping("/getCollectionStatus")
    public Result getCollectionStatus(Long articleId) {
        return collectionRecordService.getCollectionStatus(articleId) ? new Result(true, StatusCode.OK, "查询成功", true)
                : new Result(true, StatusCode.OK, "查询成功", false);
    }

    @DeleteMapping("/cancelCollection")
    public Result cancelCollection(Long collectionId) {
        collectionRecordService.cancelCollection(collectionId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("/getAllCollectionRecord/{page}/{size}")
    public Result getAllCollectionRecord(@PathVariable("page") int page, @PathVariable("size") int size) {
        Page<CollectionRecord> allCollectionRecord = collectionRecordService.getAllCollectionRecord(size, page);
        return new Result(true, StatusCode.OK, "获取成功",
                new PageResult<>(allCollectionRecord.getTotalElements(), allCollectionRecord.getContent()));
    }
}

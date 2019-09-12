package com.sicau.platform.controller;

import com.sicau.platform.entity.PageResult;
import com.sicau.platform.entity.PunchInRecord;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.service.impl.PunchInRecordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyuehe
 * @description 打卡状态查询（个人&管理员）
 * @date 2019/8/14
 */
@RestController
public class PunchInController {
    private final PunchInRecordService punchInRecordService;

    public PunchInController(PunchInRecordService punchInRecordService) {
        this.punchInRecordService = punchInRecordService;
    }

    @GetMapping("/getPunchInStatus")
    public Result getPunchInStatus() {
        return punchInRecordService.isPunchIn() ? new Result(true, StatusCode.OK, "本日打卡完成")
                : new Result(false, StatusCode.MISSION_NOT_COMPLETED, "未打卡，本日任务未完成");
    }

    @GetMapping("/getPunchInRecord/{size}/{page}")
    public Result getPunchInRecord(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<PunchInRecord> punchInRecord = punchInRecordService.getPunchInRecord(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<>(punchInRecord.getTotalElements(), punchInRecord.getContent()));

    }
}

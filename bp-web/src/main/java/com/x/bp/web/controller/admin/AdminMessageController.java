package com.x.bp.web.controller.admin;

import com.x.bp.common.model.ServicePageResult;
import com.x.bp.core.dto.message.MessageDTO;
import com.x.bp.core.dto.message.MessagePageReq;
import com.x.bp.core.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户管理
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@RestController
@RequestMapping("/admin/message")
@Api(value = "后台消息管理")
public class AdminMessageController {
    @Resource
    private MessageService messageService;

    @ApiOperation(value = "消息列表")
    @PostMapping("/list")
    public ServicePageResult<MessageDTO> list(@RequestBody @Validated MessagePageReq req) {
        return messageService.list(req);
    }


}

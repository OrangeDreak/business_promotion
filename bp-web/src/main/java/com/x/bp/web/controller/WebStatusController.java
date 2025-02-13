package com.x.bp.web.controller;

import com.x.bp.core.common.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class WebStatusController {

    @RequestMapping(value = "/webStatus", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<String> webStatus() {
        return Result.buildSuccess();
    }

}

package com.x.bp.web.controller;

import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.core.common.Result;
import com.x.bp.web.annotion.LoginNotRequired;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class WebStatusController {

    @RequestMapping(value = "/webStatus", method = {RequestMethod.GET, RequestMethod.POST})
    @LoginNotRequired
    public ServiceResultTO<String> webStatus() {
        return ServiceResultTO.buildSuccess();
    }

}

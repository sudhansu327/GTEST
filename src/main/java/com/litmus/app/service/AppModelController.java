package com.litmus.app.service;


import com.litmus.app.dto.AppsRequest;
import com.litmus.app.dto.AppsResponse;
import com.litmus.app.util.LitmusLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AppModelController {


    @Autowired
    private AppModelService appModelService;

    @PostMapping(path = "/saveAppModel", consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
    public AppsResponse saveModel(@RequestBody AppsRequest appsRequest){

        Map logMap = LitmusLogUtil.createMap("Begin saveModel", "AppModelController","saveModel");
        LitmusLogUtil.logInfo(logMap);

        if(appsRequest == null && MapUtils.isNotEmpty(appsRequest.getApps())){
            return AppsResponse.failed_save_response;
        }
        return appModelService.verifyAndSaveModelData(appsRequest);
    }

    @GetMapping(path = "/deleteApp/{appName}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public AppsResponse deleteApp(@PathVariable("appName") String appName){

        Map logMap = LitmusLogUtil.createMap("Begin deleteApp", "AppModelController","deleteApp");
        LitmusLogUtil.logInfo(logMap);

        if(StringUtils.isBlank(appName)){
            return AppsResponse.failed_delete_response;
        }
        return appModelService.verifyAndDeleteApp(appName);
    }

    @GetMapping(path = "getAppModel", produces =  MediaType.APPLICATION_JSON_VALUE)
    public AppsRequest getAppModel(){
        Map logMap = LitmusLogUtil.createMap("Begin getAppModel", "AppModelController","getAppModel");
        LitmusLogUtil.logInfo(logMap);
        return appModelService.getAppModel();
    }

    @GetMapping(path = "testOk", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getTestResponse(){
        Map logMap = LitmusLogUtil.createMap("Begin testOk", "AppModelController","getTestResponse");
        LitmusLogUtil.logInfo(logMap);
        return "TestOK";

    }

}

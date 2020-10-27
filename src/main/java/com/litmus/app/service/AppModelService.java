package com.litmus.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litmus.app.dao.AppModel;
import com.litmus.app.dto.App;
import com.litmus.app.dto.AppsRequest;
import com.litmus.app.dto.AppsResponse;
import com.litmus.app.repo.AppModelRepository;
import com.litmus.app.util.LitmusLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Component("appModelService")
public class AppModelService {

    @Autowired
    private AppModelRepository appModelRepository;

    public AppsResponse verifyAndSaveModelData(AppsRequest appsRequest) {
        Map logMap = LitmusLogUtil.createMap("Begin verifyAndSaveModelData", "AppModelService","verifyAndSaveModelData");
        LitmusLogUtil.logInfo(logMap);

        ObjectMapper mapper = new ObjectMapper();
        AppModel appModel = new AppModel();
        for(String appName: appsRequest.getApps().keySet()) {
            appModel.setAppName(appName);
            App appData = appsRequest.getApps().get(appName);
            try {
                appModel.setJsonConfig(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appData));
            } catch (Exception e) {
                LitmusLogUtil.logError("Exception Occurred", logMap, e);
                e.printStackTrace();
                return AppsResponse.failed_save_response;
            }
            appModelRepository.save(appModel);
        }
        return AppsResponse.success_save_response;
    }

    public AppsResponse verifyAndDeleteApp(String appName) {
        Map logMap = LitmusLogUtil.createMap("Begin verifyAndSaveModelData", "AppModelService","verifyAndDeleteApp");
        LitmusLogUtil.logInfo(logMap);
        appModelRepository.deleteById(appName);
        return AppsResponse.success_delete_response;
    }

    public AppsRequest getAppModel() {
        Map logMap = LitmusLogUtil.createMap("Begin verifyAndSaveModelData", "AppModelService","getAppModel");
        LitmusLogUtil.logInfo(logMap);
        ObjectMapper mapper = new ObjectMapper();
        AppsRequest appsRequest = new AppsRequest();
        List<AppModel> appModels = appModelRepository.findAll();
        if(CollectionUtils.isNotEmpty(appModels)){
            for(AppModel appModel:appModels){
                try{
                    appsRequest.addApp(appModel.getAppName(), mapper.readValue(appModel.getJsonConfig(), App.class));
                }catch (Exception e){
                    LitmusLogUtil.logError("Exception Occurred", logMap, e);
                    e.printStackTrace();
                }
            }
        }

        return appsRequest;
    }
}

package com.litmus.app.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class LitmusLogUtil {

    public static Map createMap(String message, String className, String methodname  ){
        Map logMap = new HashMap();
        logMap.put("Message", message);
        logMap.put("ClassName", className);
        logMap.put("Method", methodname);
        return logMap;

    }
    public static void logInfo(Map logMap){

        log.info(formatLog(logMap));
    }

    public static void logDebug(Map logMap){

        log.debug(formatLog(logMap));
    }


    public static void logError(Map logMap, Throwable ex){

        log.error(formatLog(logMap), ex);
    }

    public static void logError(String message, Map logMap, Throwable ex){
        logMap.put("Message", message);
        log.error(formatLog(logMap), ex);
    }

    public static void logError(Map logMap){

        log.error(formatLog(logMap));
    }
    private static String formatLog(Map<String, String> logMap) {
        return logMap.entrySet().stream().map(m -> m.getKey() +"=" +m.getValue()).collect(Collectors.joining("||"));
    }

}

package com.stu.cx.star.Validation;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: riskychan
 * @Description:校验器处理之后存储
 * @Date: Create in 16:48 2019/9/18
 */
public class ValidationResult {
    private boolean hasError = false;
    private Map<String,String> errMap = new HashMap<>();

    public void  isHasError(boolean hasError){
        this.hasError = hasError;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrMap() {
        return errMap;
    }

    public void setErrMap(Map<String, String> errMap) {
        this.errMap = errMap;
    }
    //转换获取信息
    public String getErrMsg(){
        return StringUtils.join(errMap.values().toArray(),",");
    }
}

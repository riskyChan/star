package com.stu.cx.star.Validation;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * @Author: riskychan
 * @Description:校验器
 * @Date: Create in 16:48 2019/9/18
 */
@Component
public class Validator implements InitializingBean {
    private javax.validation.Validator validator;

    //实现校验方法
    public ValidationResult validate(Object bean) {
        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        //大于0表示有错误
        if (constraintViolationSet.size() > 0) {
            validationResult.setHasError(true);
            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                validationResult.getErrMap().put(propertyName, errMsg);
            });
        }
        return validationResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate的vailidator通过工厂的方式实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}
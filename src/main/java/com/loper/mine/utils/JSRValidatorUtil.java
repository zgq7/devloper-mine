package com.loper.mine.utils;

import com.loper.mine.utils.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author liaonanzhou
 * @date 2021/6/4 10:12
 * @description
 **/
@Slf4j
public class JSRValidatorUtil {

    private final static Validator VALIDATOR = Validation.byProvider(HibernateValidator.class)
            .configure()
            .buildValidatorFactory().getValidator();

    public static <T> void validate(T param) {
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(param);
        validate.forEach(v -> {
            log.error("JSR校验异常，property:{}，message:{}", v.getPropertyPath(), v.getMessage());
            throw new ServiceException(v.getMessage());
        });
    }
}

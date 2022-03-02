package com.loper.mine.common.annotions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author liaonanzhou
 * @date 2021/6/4 10:47
 * @description
 **/
public class PasswordValidation implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean haveDigit = false;
        boolean uppercase = false;
        boolean lowercase = false;
        boolean special = false;

        char v;
        for (int i = 0; i < value.length(); i++) {
            v = value.charAt(i);
            if (Character.isDigit(v))
                haveDigit = true;
            else if (Character.isUpperCase(v))
                uppercase = true;
            else if (Character.isLowerCase(v))
                lowercase = true;
        }
        if (Pattern.compile("[ _`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？]|\n|\r|\t").matcher(value).find())
            special = true;

        return haveDigit && uppercase && lowercase && special;
    }
}

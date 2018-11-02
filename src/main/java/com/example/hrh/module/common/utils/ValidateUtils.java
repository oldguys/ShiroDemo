package com.example.hrh.module.common.utils;

import com.example.hrh.module.common.exceptions.FormValidException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

/**
 * 校验工具类
 *
 * @author huangrenhao
 * @version V1.0
 * @ClassName: VaildateUtils
 * @Description: TODO
 * @date 2017年12月5日 上午11:32:02
 */
public class ValidateUtils {

    private ValidateUtils() {
    }

    /**
     * 表单校验
     * @param bindingResult
     */
    public static void validate(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new FormValidException(errorMessage);
        }
    }

}

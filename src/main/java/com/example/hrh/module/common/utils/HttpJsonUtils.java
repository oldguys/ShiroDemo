package com.example.hrh.module.common.utils;


import com.example.hrh.module.common.dto.HttpJsonResult;
import org.springframework.http.HttpStatus;

/**
 * @author huangrenhao
 * @date 2018/6/11
 */
public class HttpJsonUtils {

    private static int CODE_SUCCESS = 1;
    private static int CODE_FAILURE = 0;

    public static final HttpJsonResult OK = build(CODE_SUCCESS);
    public static final HttpJsonResult ERROR = build(CODE_FAILURE);

    private HttpJsonUtils() {
    }

    private static HttpJsonResult build(int flag) {

        if (flag == CODE_SUCCESS) {
            return build(HttpStatus.OK, null, null);
        }

        if (flag == CODE_FAILURE) {
            return build(HttpStatus.INTERNAL_SERVER_ERROR, null, null);
        }

        return null;
    }

    public static HttpJsonResult buildSuccess(String message) {

        return build(HttpStatus.OK, message, null);
    }

    public static HttpJsonResult buildSuccess(String message, Object object) {

        return build(HttpStatus.OK, message, object);
    }

    public static HttpJsonResult buildError(String message, Object object) {

        return build(HttpStatus.INTERNAL_SERVER_ERROR, message, object);
    }

    public static HttpJsonResult buildError(String message) {

        return build(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }

    public static HttpJsonResult buildValidate(String message) {

        return build(HttpStatus.BAD_REQUEST, message, null);
    }

    public static HttpJsonResult build(HttpStatus httpStatus, String message, Object object) {

       return build(httpStatus.value(), httpStatus.getReasonPhrase(), message, object);
    }

    public static HttpJsonResult build(int code, String status, String message, Object object) {

        HttpJsonResult result = new HttpJsonResult();
        result.setCode(code);
        result.setStatus(status);
        result.setMessage(message);
        result.setObject(object);
        return result;
    }
}

package com.siemens.mindsphere.maa.common.errors.response;

import java.util.Date;

/**
 * Created by wsdgc4 on 3/3/2018.
 */
public class ErrorResponse<T> {
    private Date timestamp;

    private String message;

    private Class exception;

    private String path;

    private T errorDetail;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public T getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(T errorDetail) {
        this.errorDetail = errorDetail;
    }

    public Class<Exception> getException() {
        return exception;
    }

    public void setException(Class exception) {
        this.exception = exception;
    }
}

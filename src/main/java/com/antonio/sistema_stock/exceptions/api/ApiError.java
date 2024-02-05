package com.antonio.sistema_stock.exceptions.api;

import java.time.LocalDateTime;

public class ApiError {
    private Integer errorCode;
    private String errorDesc;
    private LocalDateTime date;

    public ApiError(Integer errorCode, String errorDesc, LocalDateTime date) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.date = date;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}